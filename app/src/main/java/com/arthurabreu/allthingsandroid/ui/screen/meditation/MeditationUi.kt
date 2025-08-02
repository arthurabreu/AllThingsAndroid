package com.arthurabreu.allthingsandroid.ui.screen.meditation

import android.R.attr.maxHeight
import android.R.attr.maxWidth
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arthurabreu.allthingsandroid.R
import com.arthurabreu.allthingsandroid.data.meditation.FeatureMeditation
import com.arthurabreu.allthingsandroid.ui.theme.AquaBlue
import com.arthurabreu.allthingsandroid.ui.theme.Beige1
import com.arthurabreu.allthingsandroid.ui.theme.Beige2
import com.arthurabreu.allthingsandroid.ui.theme.Beige3
import com.arthurabreu.allthingsandroid.ui.theme.BlueViolet1
import com.arthurabreu.allthingsandroid.ui.theme.BlueViolet2
import com.arthurabreu.allthingsandroid.ui.theme.BlueViolet3
import com.arthurabreu.allthingsandroid.ui.theme.ButtonBlue
import com.arthurabreu.allthingsandroid.ui.theme.DarkerButtonBlue
import com.arthurabreu.allthingsandroid.ui.theme.DeepBlue
import com.arthurabreu.allthingsandroid.ui.theme.LightGreen1
import com.arthurabreu.allthingsandroid.ui.theme.LightGreen2
import com.arthurabreu.allthingsandroid.ui.theme.LightGreen3
import com.arthurabreu.allthingsandroid.ui.theme.LightRed
import com.arthurabreu.allthingsandroid.ui.theme.OrangeYellow1
import com.arthurabreu.allthingsandroid.ui.theme.OrangeYellow2
import com.arthurabreu.allthingsandroid.ui.theme.OrangeYellow3
import com.arthurabreu.allthingsandroid.ui.theme.TextWhite
import com.arthurabreu.allthingsandroid.utils.standardQuadFromTo

@Composable
fun HomeMeditation() {
    Box(
        modifier = Modifier
            .background(DeepBlue)
            .fillMaxSize()
    ) {
        Column {
            GreetingSection()
            ChipSection(
                chips = listOf(
                    stringResource(R.string.sweet_sleep),
                    stringResource(R.string.insomnia),
                    stringResource(R.string.depression)
                )
            )
            CurrentMeditationSection()
            FeatureMeditationSection(
                featureMeditations = listOf(
                    FeatureMeditation(
                        title = "Sleep meditation",
                        iconId = R.drawable.ic_headphone,
                        lightColor = BlueViolet1,
                        mediumColor = BlueViolet2,
                        darkColor = BlueViolet3
                    ),
                    FeatureMeditation(
                        title = "Tips for sleeping",
                        iconId = R.drawable.ic_videocam,
                        lightColor = LightGreen1,
                        mediumColor = LightGreen2,
                        darkColor = LightGreen3
                    ),
                    FeatureMeditation(
                        title = "Night island",
                        iconId = R.drawable.ic_headphone,
                        lightColor = OrangeYellow1,
                        mediumColor = OrangeYellow2,
                        darkColor = OrangeYellow3
                    ),
                    FeatureMeditation(
                        title = "Calming sounds",
                        iconId = R.drawable.ic_headphone,
                        lightColor = Beige1,
                        mediumColor = Beige2,
                        darkColor = Beige3
                    )
                )
            )
        }
        BottomMenu(
            items = listOf(
                BottomMenuContent("Home", R.drawable.ic_home),
                BottomMenuContent("Meditate", R.drawable.ic_bubble),
                BottomMenuContent("Sleep", R.drawable.ic_moon),
                BottomMenuContent("Music", R.drawable.ic_music),
                BottomMenuContent("Profile", R.drawable.ic_profile),
                ),
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun GreetingSection(
    name: String = stringResource(R.string.arthur_abreu)
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.good_morning,  name),
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = stringResource(R.string.we_wish_you_have_a_good_day),
                style = MaterialTheme.typography.bodySmall
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = stringResource(R.string.search),
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun ChipSection(
    chips: List<String>
) {
    var selectedChipIndex by remember { mutableIntStateOf(0) }
    LazyRow {
        items(chips.size) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(start = 15.dp, top = 15.dp, bottom = 15.dp)
                    .clickable { selectedChipIndex = it }
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (selectedChipIndex == it) ButtonBlue else DarkerButtonBlue)
                    .padding(15.dp)
            ) {
                Text(text = chips[it], color = TextWhite)
            }
        }
    }
}

@Composable
fun CurrentMeditationSection(
    color: Color = LightRed
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color)
            .padding(horizontal = 15.dp, vertical = 20.dp)
            .fillMaxWidth()
    ) {
        Column {
            Text(
                text = stringResource(R.string.daily_thought),
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = stringResource(R.string.meditation_3_10_min),
                style = MaterialTheme.typography.bodySmall,
                color = TextWhite
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(ButtonBlue)
                .padding(10.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_play),
                contentDescription = stringResource(R.string.play),
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun FeatureMeditationSection(
    featureMeditations: List<FeatureMeditation>
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.featuremeditations),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(15.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp, bottom = 100.dp),
            modifier = Modifier.fillMaxHeight()
        ) {
            items(featureMeditations.size) {
                FeatureMeditationItem(featureMeditation = featureMeditations[it])
            }
        }
    }
}

@Composable
fun FeatureMeditationItem(
    featureMeditation: FeatureMeditation
) {
    Box(
        modifier = Modifier
            .padding(7.5.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(featureMeditation.darkColor)
    ) {
        val width = maxWidth
        val height = maxHeight

        val mediumColoredPoint1 = Offset(0f, height * 0.3f)
        val mediumColoredPoint2 = Offset(width * 0.1f, height * 0.35f)
        val mediumColoredPoint3 = Offset(width * 0.4f, height * 0.05f)
        val mediumColoredPoint4 = Offset(width * 0.75f, height * 0.7f)
        val mediumColoredPoint5 = Offset(width * 1.4f, -height.toFloat())

        val mediumColoredPath = Path().apply {
            moveTo(mediumColoredPoint1.x, mediumColoredPoint1.y)
            standardQuadFromTo(mediumColoredPoint1, mediumColoredPoint2)
            standardQuadFromTo(mediumColoredPoint2, mediumColoredPoint3)
            standardQuadFromTo(mediumColoredPoint3, mediumColoredPoint4)
            standardQuadFromTo(mediumColoredPoint4, mediumColoredPoint5)
            lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
            lineTo(-100f,height.toFloat() + 100f)
            close()
        }

        val lightPoint1 = Offset(0f, height * 0.35f)
        val lightPoint2 = Offset (width * 0.1f, height * 0.4f)
        val lightPoint3 = Offset(width * 0.3f, height * 0.35f)
        val lightPoint4 = Offset(width * 0.65f, height.toFloat())
        val lightPoint5 = Offset(width * 1.4f, -height.toFloat() / 3f)
        val lightColoredPath = Path().apply {
            moveTo(lightPoint1.x, lightPoint1.y)
            standardQuadFromTo(lightPoint1, lightPoint2)
            standardQuadFromTo(lightPoint2, lightPoint3)
            standardQuadFromTo(lightPoint3, lightPoint4)
            standardQuadFromTo(lightPoint4, lightPoint5)
            lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
            lineTo(-100f, height.toFloat() + 100f)
            close()
        }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            drawPath(mediumColoredPath, color = featureMeditation.mediumColor)
            drawPath(lightColoredPath, color = featureMeditation.lightColor)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Text(
                text = featureMeditation.title,
                style = MaterialTheme.typography.headlineMedium,
                lineHeight = 26.sp,
                modifier = Modifier.align(Alignment.TopStart)
            )
            Icon(
                painter = painterResource(id = featureMeditation.iconId),
                contentDescription = featureMeditation.title,
                tint = Color.White,
                modifier = Modifier.align(Alignment.BottomStart)
            )
            Text(
                text = stringResource(R.string.start),
                color = TextWhite,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable {

                    }
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(10.dp))
                    .background(ButtonBlue)
                    .padding(vertical = 6.dp, horizontal = 15.dp)
            )
        }
    }
}

data class BottomMenuContent(val title: String, val iconId: Int)

@Composable
fun BottomMenu(
    items: List<BottomMenuContent>,
    modifier: Modifier = Modifier,
    activeHighlightColor: Color = ButtonBlue,
    activeTextColor: Color = Color.White,
    inactiveTextColor: Color = AquaBlue,
    initialSelectedItemIndex: Int = 0
) {
    var selectedItemIndex by remember {
        mutableIntStateOf(initialSelectedItemIndex)
    }
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(DeepBlue)
            .padding(15.dp)
    ) {
        items.forEachIndexed { index, item ->
            BottomMenuItem(
                item = item,
                isSelected = index == selectedItemIndex,
                activeHighlightColor = activeHighlightColor,
                activeTextColor = activeTextColor,
                inactiveTextColor = inactiveTextColor
            ) {
                selectedItemIndex = index
            }
        }
    }
}

@Composable
fun BottomMenuItem(
    item: BottomMenuContent,
    isSelected: Boolean = false,
    activeHighlightColor: Color = ButtonBlue,
    activeTextColor: Color = Color.White,
    inactiveTextColor: Color = AquaBlue,
    onItemClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.clickable {
            onItemClick()
        }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(if (isSelected) activeHighlightColor else Color.Transparent)
                .padding(10.dp)
        ) {
            Icon(
                painter = painterResource(id = item.iconId),
                contentDescription = item.title,
                tint = if (isSelected) activeTextColor else inactiveTextColor,
                modifier = Modifier.size(20.dp)
            )
        }
        Text(
            text = item.title,
            color = if(isSelected) activeTextColor else inactiveTextColor
        )
    }
}