#!/usr/bin/env python3
from pathlib import Path
from textwrap import dedent

ROOT = Path(__file__).resolve().parents[1]


def w(rel: str, content: str):
    path = ROOT / rel
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(dedent(content).lstrip("\n") if content.startswith("\n") else content)


LIB = '''plugins {{
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}}

android {{
    namespace = "{ns}"
    compileSdk = 35
    defaultConfig {{
        minSdk = 30
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }}
    compileOptions {{
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }}
    kotlin {{
        compilerOptions {{
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        }}
    }}
    buildFeatures {{ compose = true }}
    tasks.withType<Test> {{ useJUnitPlatform() }}
}}

dependencies {{
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
    implementation(project(":core:navigation"))
    {extra}
    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.kotlinx.coroutines.android)
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)
}}
'''


def module(name: str, extra: str = ""):
    path = f"feature/{name}"
    ns = f"com.arthurabreu.allthingsandroid.feature.{name.replace('-', '')}"
    w(f"{path}/build.gradle.kts", LIB.format(ns=ns, extra=extra))
    w(f"{path}/src/main/AndroidManifest.xml", '<?xml version="1.0" encoding="utf-8"?>\n<manifest />\n')
    w(f"{path}/consumer-rules.pro", "\n")
    w(f"{path}/proguard-rules.pro", "\n")
    w(f"{path}/README.md", f"# :feature:{name}\n\nPortfolio feature module. Flavor `demo` uses fakes.\n")
    return path, ns


# HOME
p, ns = module("home")
w(f"{p}/src/main/java/{ns.replace('.', '/')}/CatalogScreen.kt", f'''
package {ns}

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.collectAsState
import com.arthurabreu.allthingsandroid.core.domain.PortfolioCatalog
import com.arthurabreu.allthingsandroid.core.model.CatalogSection
import com.arthurabreu.allthingsandroid.core.ui.FeatureCard
import com.arthurabreu.allthingsandroid.core.ui.SectionTitle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class CatalogState(val sections: List<CatalogSection> = PortfolioCatalog.sections())

class CatalogViewModel : ViewModel() {{
    private val _state = MutableStateFlow(CatalogState())
    val state: StateFlow<CatalogState> = _state.asStateFlow()

    fun routeFor(id: String): String? =
        _state.value.sections.flatMap {{ it.items }}.find {{ it.id == id }}?.route
}}

@Composable
fun CatalogScreen(viewModel: CatalogViewModel, onOpen: (String) -> Unit) {{
    val state by viewModel.state.collectAsState()
    LazyColumn(Modifier.padding(16.dp).testTag("catalog-list")) {{
        state.sections.forEach {{ section ->
            item {{ SectionTitle(section.title) }}
            items(section.items, key = {{ it.id }}) {{ item ->
                FeatureCard(item) {{ onOpen(item.route) }}
            }}
        }}
    }}
}}
''')
w(f"{p}/src/test/java/{ns.replace('.', '/')}/CatalogViewModelTest.kt", f'''
package {ns}

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CatalogViewModelTest {{
    private val vm = CatalogViewModel()

    @Test
    fun sectionsEndWithLab() {{
        assertEquals("Lab / Design System", vm.state.value.sections.last().title)
    }}

    @Test
    fun routeForShop() {{
        assertEquals("shop", vm.routeFor("shop"))
    }}

    @Test
    fun unknownIdReturnsNull() {{
        assertEquals(null, vm.routeFor("missing"))
    }}

    @Test
    fun hasQualityAndField() {{
        val titles = vm.state.value.sections.map {{ it.title }}
        assertTrue(titles.containsAll(listOf("Quality", "Field", "Realtime")))
    }}
}}
''')
w(f"{p}/src/androidTest/java/{ns.replace('.', '/')}/CatalogScreenTest.kt", f'''
package {ns}

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.assertIsDisplayed
import org.junit.Rule
import org.junit.Test

class CatalogScreenTest {{
    @get:Rule val rule = createComposeRule()

    @Test
    fun showsCatalogList() {{
        rule.setContent {{ CatalogScreen(CatalogViewModel()) {{ }} }}
        rule.onNodeWithTag("catalog-list").assertIsDisplayed()
    }}
}}
''')

# Generic feature helper
def simple_feature(name: str, title: str, tag: str, body_extra: str, vm_extra: str, tests: str, extra_deps: str = ""):
    path, ns = module(name, extra_deps)
    pkg = ns
    folder = pkg.replace(".", "/")
    w(f"{path}/src/main/java/{folder}/{title}Screen.kt", f'''
package {pkg}

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
{vm_extra}

@Composable
fun {title}Screen(viewModel: {title}ViewModel, onBack: () -> Unit = {{}}) {{
    val state by viewModel.state.collectAsState()
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).testTag("{tag}"),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {{
        Text("{title}", style = MaterialTheme.typography.headlineSmall)
        {body_extra}
        Button(onClick = onBack) {{ Text("Back") }}
    }}
}}
''')
    w(f"{path}/src/test/java/{folder}/{title}ViewModelTest.kt", f'''
package {pkg}

import org.junit.jupiter.api.Test
{tests}
''')
    w(f"{path}/src/androidTest/java/{folder}/{title}ScreenTest.kt", f'''
package {pkg}

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class {title}ScreenTest {{
    @get:Rule val rule = createComposeRule()

    @Test
    fun showsScreen() {{
        rule.setContent {{ {title}Screen({title}ViewModel()) }}
        rule.onNodeWithTag("{tag}").assertIsDisplayed()
    }}
}}
''')
    return path, ns


simple_feature(
    "persistence",
    "Persistence",
    "persistence-screen",
    '''
        state.versions.forEach { info ->
            Text("v${info.version} · ${info.migrationKind} · ${info.description}")
        }
        Text("Current schema: v${state.currentVersion}", modifier = Modifier.testTag("schema-version"))
        Button(onClick = viewModel::seedNote) { Text("Insert note") }
        state.notes.forEach { Text(it) }
    ''',
    '''
import com.arthurabreu.allthingsandroid.core.domain.SchemaStory
import com.arthurabreu.allthingsandroid.core.model.SchemaInfo

data class PersistenceState(
    val versions: List<SchemaInfo> = SchemaStory.versions(),
    val currentVersion: Int = 3,
    val notes: List<String> = emptyList(),
)

class PersistenceViewModel : ViewModel() {
    private val _state = MutableStateFlow(PersistenceState())
    val state: StateFlow<PersistenceState> = _state.asStateFlow()

    fun seedNote() {
        _state.update {
            it.copy(notes = it.notes + "note-${it.notes.size + 1} @ v${it.currentVersion}")
        }
    }
}
''',
    '''
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

class PersistenceViewModelTest {
    @Test
    fun startsAtV3() {
        assertEquals(3, PersistenceViewModel().state.value.currentVersion)
    }

    @Test
    fun seedAppendsNote() {
        val vm = PersistenceViewModel()
        vm.seedNote()
        assertTrue(vm.state.value.notes.first().contains("v3"))
    }
}
''',
)

simple_feature(
    "lists",
    "Lists",
    "lists-screen",
    '''
        androidx.compose.material3.OutlinedTextField(
            value = state.query,
            onValueChange = viewModel::onQuery,
            label = { Text("Search") },
            modifier = Modifier.testTag("lists-search"),
        )
        if (state.error != null) {
            Text(state.error!!, modifier = Modifier.testTag("lists-error"))
            Button(onClick = viewModel::retry) { Text("Retry") }
        } else if (state.visible.isEmpty()) {
            Text("Empty", modifier = Modifier.testTag("lists-empty"))
        } else {
            state.visible.forEach { Text("${it.title} — ${it.body}") }
        }
        Button(onClick = viewModel::fail) { Text("Simulate error") }
    ''',
    '''
import com.arthurabreu.allthingsandroid.core.domain.ListFilter
import com.arthurabreu.allthingsandroid.core.domain.SeedRows
import com.arthurabreu.allthingsandroid.core.model.ListRow

data class ListsState(
    val query: String = "",
    val visible: List<ListRow> = emptyList(),
    val error: String? = null,
)

class ListsViewModel(
    private val filter: ListFilter = ListFilter(),
    private val seed: List<ListRow> = SeedRows.generate(),
) : ViewModel() {
    private val _state = MutableStateFlow(ListsState(visible = seed))
    val state: StateFlow<ListsState> = _state.asStateFlow()

    fun onQuery(value: String) {
        _state.update { it.copy(query = value, visible = filter.apply(seed, value), error = null) }
    }

    fun fail() { _state.update { it.copy(error = "Network unavailable", visible = emptyList()) } }
    fun retry() { onQuery(_state.value.query) }
}
''',
    '''
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue

class ListsViewModelTest {
    @Test
    fun filtersBlocked() {
        val vm = ListsViewModel()
        vm.onQuery("blocked")
        assertTrue(vm.state.value.visible.all { it.body.contains("Blocked") })
    }

    @Test
    fun failThenRetryClearsError() {
        val vm = ListsViewModel()
        vm.fail()
        assertEquals("Network unavailable", vm.state.value.error)
        vm.retry()
        assertNull(vm.state.value.error)
        assertTrue(vm.state.value.visible.isNotEmpty())
    }
}
''',
)

simple_feature(
    "shop",
    "Shop",
    "shop-screen",
    '''
        Text("Total: ${state.totalCents} cents", modifier = Modifier.testTag("cart-total"))
        state.message?.let { Text(it, modifier = Modifier.testTag("shop-message")) }
        state.products.forEach { product ->
            Button(onClick = { viewModel.add(product.id) }, modifier = Modifier.testTag("add-${product.id}")) {
                Text("Add ${product.name}")
            }
        }
        state.lines.forEach { line ->
            Button(onClick = { viewModel.remove(line.product.id) }) {
                Text("${line.product.name} x${line.quantity}")
            }
        }
        Button(onClick = viewModel::checkout) { Text("Checkout") }
    ''',
    '''
import com.arthurabreu.allthingsandroid.core.common.AppResult
import com.arthurabreu.allthingsandroid.core.domain.CartCalculator
import com.arthurabreu.allthingsandroid.core.domain.ShopCatalog
import com.arthurabreu.allthingsandroid.core.model.CartLine
import com.arthurabreu.allthingsandroid.core.model.ShopProduct

data class ShopState(
    val products: List<ShopProduct> = emptyList(),
    val lines: List<CartLine> = emptyList(),
    val totalCents: Int = 0,
    val message: String? = null,
)

class ShopViewModel(
    private val catalog: ShopCatalog = ShopCatalog(),
    private val cart: CartCalculator = CartCalculator(),
) : ViewModel() {
    private val _state = MutableStateFlow(ShopState(products = catalog.products()))
    val state: StateFlow<ShopState> = _state.asStateFlow()

    fun add(id: String) {
        val product = catalog.byId(id) ?: return
        when (val result = cart.add(_state.value.lines, product)) {
            is AppResult.Ok -> _state.update {
                it.copy(lines = result.value, totalCents = cart.totalCents(result.value), message = null)
            }
            is AppResult.Err -> _state.update { it.copy(message = result.message) }
        }
    }

    fun remove(id: String) {
        val lines = cart.remove(_state.value.lines, id)
        _state.update { it.copy(lines = lines, totalCents = cart.totalCents(lines)) }
    }

    fun checkout() {
        if (_state.value.lines.isEmpty()) {
            _state.update { it.copy(message = "Cart is empty") }
        } else {
            _state.update { it.copy(lines = emptyList(), totalCents = 0, message = "Order placed") }
        }
    }
}
''',
    '''
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

class ShopViewModelTest {
    @Test
    fun addUpdatesTotal() {
        val vm = ShopViewModel()
        vm.add("sku-lager")
        assertEquals(499, vm.state.value.totalCents)
    }

    @Test
    fun stockCapShowsMessage() {
        val vm = ShopViewModel()
        repeat(4) { vm.add("sku-pack") }
        assertEquals("Out of stock", vm.state.value.message)
    }

    @Test
    fun checkoutClearsCart() {
        val vm = ShopViewModel()
        vm.add("sku-ipa")
        vm.checkout()
        assertTrue(vm.state.value.lines.isEmpty())
        assertEquals("Order placed", vm.state.value.message)
    }
}
''',
)

simple_feature(
    "maps",
    "Maps",
    "maps-screen",
    '''
        Text("Demo map — live flavor uses Maps Compose when MAPS_API_KEY is set.")
        state.pins.forEach { pin ->
            Text("${pin.title} (${pin.lat}, ${pin.lng})", modifier = Modifier.testTag("pin-${pin.id}"))
        }
        Button(onClick = viewModel::selectRoute) { Text("Show polyline") }
        if (state.showRoute) Text("Polyline: brewery -> taproom", modifier = Modifier.testTag("polyline"))
    ''',
    '''
import com.arthurabreu.allthingsandroid.core.model.MapPin

data class MapsState(
    val pins: List<MapPin> = listOf(
        MapPin("brewery", "Brewery", -23.5505, -46.6333),
        MapPin("taproom", "Taproom", -23.5614, -46.6558),
        MapPin("me", "You", -23.555, -46.64),
    ),
    val showRoute: Boolean = false,
)

class MapsViewModel : ViewModel() {
    private val _state = MutableStateFlow(MapsState())
    val state: StateFlow<MapsState> = _state.asStateFlow()
    fun selectRoute() { _state.update { it.copy(showRoute = true) } }
}
''',
    '''
import org.junit.jupiter.api.Assertions.assertTrue

class MapsViewModelTest {
    @Test
    fun polylineFlag() {
        val vm = MapsViewModel()
        vm.selectRoute()
        assertTrue(vm.state.value.showRoute)
    }
}
''',
)

simple_feature(
    "firebase",
    "Firebase",
    "firebase-screen",
    '''
        Text(if (state.signedIn) "Signed in as ${state.userLabel}" else "Signed out", modifier = Modifier.testTag("auth-state"))
        Text(state.remoteBanner, modifier = Modifier.testTag("rc-banner"))
        Text("FCM: ${state.fcmToken}")
        state.notes.forEach { Text(it) }
        Button(onClick = viewModel::toggleAuth) { Text("Toggle auth") }
        Button(onClick = viewModel::addNote) { Text("Add Firestore note") }
        Button(onClick = viewModel::upload) { Text("Upload to Storage") }
        state.lastUpload?.let { Text(it, modifier = Modifier.testTag("upload-status")) }
        Button(onClick = viewModel::crash) { Text("Crashlytics test") }
        if (state.crashed) Text("Crash recorded (demo)", modifier = Modifier.testTag("crash-flag"))
    ''',
    '''
import com.arthurabreu.allthingsandroid.core.model.FirebaseSnapshot

data class FirebaseState(
    val signedIn: Boolean = false,
    val userLabel: String = "anonymous",
    val remoteBanner: String = "Welcome (Remote Config demo)",
    val fcmToken: String = "demo-fcm-token",
    val notes: List<String> = emptyList(),
    val lastUpload: String? = null,
    val crashed: Boolean = false,
)

class FirebaseViewModel : ViewModel() {
    private val _state = MutableStateFlow(FirebaseState())
    val state: StateFlow<FirebaseState> = _state.asStateFlow()

    fun toggleAuth() {
        _state.update {
            val signed = !it.signedIn
            it.copy(signedIn = signed, userLabel = if (signed) "demo@local" else "anonymous")
        }
    }

    fun addNote() { _state.update { it.copy(notes = it.notes + "note-${it.notes.size + 1}") } }
    fun upload() { _state.update { it.copy(lastUpload = "uploaded://local/evidence.jpg") } }
    fun crash() { _state.update { it.copy(crashed = true) } }
    fun snapshot(): FirebaseSnapshot = state.value.let {
        FirebaseSnapshot(it.signedIn, it.userLabel, it.remoteBanner, it.fcmToken, it.notes, it.lastUpload)
    }
}
''',
    '''
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

class FirebaseViewModelTest {
    @Test
    fun authAndNoteAndUpload() {
        val vm = FirebaseViewModel()
        vm.toggleAuth()
        vm.addNote()
        vm.upload()
        vm.crash()
        val snap = vm.snapshot()
        assertTrue(snap.signedIn)
        assertEquals("demo@local", snap.userLabel)
        assertEquals(1, snap.notes.size)
        assertTrue(vm.state.value.crashed)
    }
}
''',
)

simple_feature(
    "chat",
    "Chat",
    "chat-screen",
    '''
        state.messages.forEach { msg ->
            Text("${msg.author}: ${msg.body}${if (msg.pending) "…" else ""}")
        }
        androidx.compose.material3.OutlinedTextField(
            value = state.draft,
            onValueChange = viewModel::onDraft,
            modifier = Modifier.testTag("chat-input"),
        )
        Button(onClick = viewModel::send, modifier = Modifier.testTag("chat-send")) { Text("Send") }
        if (state.offline) Text("Queued offline", modifier = Modifier.testTag("chat-offline"))
        Button(onClick = viewModel::toggleOffline) { Text("Toggle offline") }
    ''',
    '''
import com.arthurabreu.allthingsandroid.core.model.ChatMessage

data class ChatState(
    val draft: String = "",
    val messages: List<ChatMessage> = listOf(
        ChatMessage("m0", "bot", "Ktor WebSocket ready (demo).", false),
    ),
    val offline: Boolean = false,
)

class ChatViewModel : ViewModel() {
    private val _state = MutableStateFlow(ChatState())
    val state: StateFlow<ChatState> = _state.asStateFlow()
    private var seq = 1

    fun onDraft(value: String) { _state.update { it.copy(draft = value) } }

    fun send() {
        val text = _state.value.draft.trim()
        if (text.isEmpty()) return
        val id = "m$seq".also { seq++ }
        val outgoing = ChatMessage(id, "you", text, true, pending = _state.value.offline)
        _state.update {
            val next = it.messages + outgoing
            if (it.offline) it.copy(draft = "", messages = next)
            else it.copy(
                draft = "",
                messages = next + ChatMessage("ack-$id", "bot", "ack: $text", false),
            )
        }
    }

    fun toggleOffline() { _state.update { it.copy(offline = !it.offline) } }
}
''',
    '''
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

class ChatViewModelTest {
    @Test
    fun sendAcksWhenOnline() {
        val vm = ChatViewModel()
        vm.onDraft("hi")
        vm.send()
        assertEquals(3, vm.state.value.messages.size)
        assertTrue(vm.state.value.messages.last().body.startsWith("ack:"))
    }

    @Test
    fun offlineQueuesWithoutAck() {
        val vm = ChatViewModel()
        vm.toggleOffline()
        vm.onDraft("later")
        vm.send()
        assertTrue(vm.state.value.messages.last().pending)
    }
}
''',
)

simple_feature(
    "voice",
    "Voice",
    "voice-screen",
    '''
        Text(state.status, modifier = Modifier.testTag("voice-status"))
        Button(onClick = viewModel::record) { Text("Record chunk") }
        Button(onClick = viewModel::send) { Text("Send to server") }
        Text("Bytes in: ${state.recorded}  out: ${state.received}")
    ''',
    '''
import com.arthurabreu.allthingsandroid.core.common.AppResult
import com.arthurabreu.allthingsandroid.core.network.LocalEchoTransport
import com.arthurabreu.allthingsandroid.core.network.VoiceTransport

data class VoiceState(
    val status: String = "Idle — server optional, local echo fallback",
    val recorded: Int = 0,
    val received: Int = 0,
    val buffer: ByteArray = ByteArray(0),
)

class VoiceViewModel(
    private val transport: VoiceTransport = LocalEchoTransport(),
) : ViewModel() {
    private val _state = MutableStateFlow(VoiceState())
    val state: StateFlow<VoiceState> = _state.asStateFlow()

    fun record() {
        val chunk = ByteArray(320) { 1 }
        _state.update { it.copy(buffer = it.buffer + chunk, recorded = it.recorded + chunk.size, status = "Recorded") }
    }

    fun sendSync() {
        when (val result = transport.sendPcm(_state.value.buffer)) {
            is AppResult.Ok -> _state.update {
                it.copy(received = result.value.size, status = "Played local echo", buffer = ByteArray(0))
            }
            is AppResult.Err -> _state.update { it.copy(status = result.message) }
        }
    }
}
''',
    '''
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

class VoiceViewModelTest {
    @Test
    fun recordAndEcho() = runTest {
        val vm = VoiceViewModel()
        vm.record()
        vm.send()
        assertEquals(320, vm.state.value.received)
        assertTrue(vm.state.value.status.contains("echo"))
    }
}
''',
    extra_deps='    implementation(project(":core:network"))',
)

simple_feature(
    "feedback",
    "Feedback",
    "feedback-screen",
    '''
        Text("CSAT / field feedback (generic — no client IP)")
        state.message?.let { Text(it, modifier = Modifier.testTag("feedback-message")) }
        listOf(1, 2, 3, 4, 5).forEach { score ->
            Button(onClick = { viewModel.setScore(score) }, modifier = Modifier.testTag("score-$score")) {
                Text("$score")
            }
        }
        androidx.compose.material3.OutlinedTextField(
            value = state.comment,
            onValueChange = viewModel::setComment,
            label = { Text("Comment") },
            modifier = Modifier.testTag("feedback-comment"),
        )
        Button(onClick = viewModel::attachPhoto) { Text("Attach photo (demo)") }
        Button(onClick = viewModel::submit, modifier = Modifier.testTag("feedback-submit")) { Text("Queue sync") }
        state.drafts.forEach { Text("#${it.score} ${it.comment} synced=${it.synced}") }
    ''',
    '''
import com.arthurabreu.allthingsandroid.core.common.AppResult
import com.arthurabreu.allthingsandroid.core.domain.FeedbackRules
import com.arthurabreu.allthingsandroid.core.model.FeedbackDraft

data class FeedbackState(
    val score: Int = 0,
    val comment: String = "",
    val photoUri: String? = null,
    val drafts: List<FeedbackDraft> = emptyList(),
    val message: String? = null,
)

class FeedbackViewModel(
    private val rules: FeedbackRules = FeedbackRules(),
) : ViewModel() {
    private val _state = MutableStateFlow(FeedbackState())
    val state: StateFlow<FeedbackState> = _state.asStateFlow()
    private var seq = 1

    fun setScore(value: Int) { _state.update { it.copy(score = value, message = null) } }
    fun setComment(value: String) { _state.update { it.copy(comment = value) } }
    fun attachPhoto() { _state.update { it.copy(photoUri = "content://demo/photo.jpg") } }

    fun submit() {
        when (val result = rules.validate(_state.value.score, _state.value.comment)) {
            is AppResult.Err -> _state.update { it.copy(message = result.message) }
            is AppResult.Ok -> {
                val draft = FeedbackDraft(
                    id = "fb-$seq",
                    score = _state.value.score,
                    comment = _state.value.comment,
                    photoUri = _state.value.photoUri,
                    synced = false,
                )
                seq++
                _state.update {
                    it.copy(
                        drafts = it.drafts + draft,
                        score = 0,
                        comment = "",
                        photoUri = null,
                        message = "Queued for WorkManager sync",
                    )
                }
            }
        }
    }
}
''',
    '''
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

class FeedbackViewModelTest {
    @Test
    fun rejectsLowScoreWithoutComment() {
        val vm = FeedbackViewModel()
        vm.setScore(1)
        vm.submit()
        assertEquals("Low scores need a comment", vm.state.value.message)
    }

    @Test
    fun queuesValidFeedback() {
        val vm = FeedbackViewModel()
        vm.setScore(4)
        vm.setComment("Cold chain ok")
        vm.attachPhoto()
        vm.submit()
        assertEquals(1, vm.state.value.drafts.size)
        assertTrue(vm.state.value.message!!.contains("Queued"))
    }
}
''',
)

simple_feature(
    "lab",
    "Leaks",
    "leaks-screen",
    '''
        Text("LeakCanary is on debug builds. This button keeps a static reference on purpose.")
        Button(onClick = viewModel::toggleLeak, modifier = Modifier.testTag("leak-toggle")) {
            Text(if (state.armed) "Disarm leak" else "Arm intentional leak")
        }
        Text(if (state.armed) "LEAK ARMED" else "safe", modifier = Modifier.testTag("leak-state"))
    ''',
    '''
data class LeaksState(val armed: Boolean = false)

class LeaksViewModel : ViewModel() {
    private val _state = MutableStateFlow(LeaksState())
    val state: StateFlow<LeaksState> = _state.asStateFlow()

    fun toggleLeak() {
        _state.update { it.copy(armed = !it.armed) }
        if (_state.value.armed) LeakHolder.ref = this else LeakHolder.ref = null
    }
}

object LeakHolder {
    var ref: Any? = null
}
''',
    '''
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue

class LeaksViewModelTest {
    @Test
    fun armsAndDisarms() {
        val vm = LeaksViewModel()
        vm.toggleLeak()
        assertTrue(vm.state.value.armed)
        assertNotNull(LeakHolder.ref)
        vm.toggleLeak()
        assertFalse(vm.state.value.armed)
        assertNull(LeakHolder.ref)
    }
}
''',
)

print("features generated")
