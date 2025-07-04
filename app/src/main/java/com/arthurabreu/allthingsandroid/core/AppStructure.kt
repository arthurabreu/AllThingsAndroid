package com.arthurabreu.allthingsandroid.core

//com.arthurabreu.allthingsandroid/
//├── application/                     // (Opcional) Classe Application customizada
//│   └── AllThingsAndroidApplication.kt
//│
//├── core/                            // (Opcional) Módulos ou código base compartilhado por features
//│   ├── di/                          // DI compartilhado
//│   │   └── CoreModule.kt
//│   ├── domain/                      // Casos de uso ou modelos de domínio centrais
//│   │   ├── model/
//│   │   │   └── User.kt
//│   │   └── usecase/
//│   │       └── GetUserSettingsUseCase.kt
//│   ├── data/                        // Repositórios centrais
//│   │   ├── repository/
//│   │   │   └── UserRepositoryImpl.kt
//│   │   └── local/
//│   │       └── AppDatabase.kt
//│   └── util/                        // Utilitários de nível core
//│       └── NetworkMonitor.kt
//│
//├── di/                              // Módulos de Injeção de Dependência de nível de App
//│   ├── AppModule.kt
//│   └── ViewModelModule.kt           // Se você separar os módulos de ViewModel
//│
//├── navigation/                      // Lógica de navegação
//│   ├── AppNavigation.kt             // Composable principal de navegação
//│   ├── Screen.kt                    // Definições de rotas/destinos
//│   └── NavGraphBuilders.kt          // (Opcional) Funções de extensão para construir grafos
//│
//├── features/                        // (Recomendado) Organização por funcionalidade
//│   ├── home/                        // Exemplo: Funcionalidade "Home"
//│   │   ├── di/                      // DI específico da feature Home
//│   │   │   └── HomeModule.kt
//│   │   ├── domain/                  // (Se a lógica de domínio for complexa e específica da feature)
//│   │   │   ├── model/
//│   │   │   │   └── FeaturedItem.kt
//│   │   │   └── usecase/
//│   │   │       └── GetFeaturedItemsUseCase.kt
//│   │   ├── data/                    // (Se a lógica de dados for complexa e específica da feature)
//│   │   │   ├── repository/
//│   │   │   │   └── HomeRepositoryImpl.kt
//│   │   │   └── remote/
//│   │   │       └── HomeApiService.kt
//│   │   └── presentation/            // UI e lógica de apresentação da feature Home
//│   │       ├── HomeScreen.kt
//│   │       ├── HomeViewModel.kt
//│   │       ├── composable/          // Componentes específicos da tela Home
//│   │       │   └── FeaturedItemCard.kt
//│   │       └── state/
//│   │           └── HomeUiState.kt
//│   │
//│   ├── settings/                    // Exemplo: Funcionalidade "Settings"
//│   │   ├── presentation/
//│   │   │   ├── SettingsScreen.kt
//│   │   │   └── SettingsViewModel.kt
//│   │   └── ... (di, domain, data se necessário)
//│   │
//│   └── feature_x/                   // Outra funcionalidade
//│       └── ...
//│
//├── ui/                              // (Alternativa se não organizar por features, ou para UI compartilhada)
//│   ├── screen/                      // (Se não estiver dentro de features/)
//│   │   ├── SplashScreen.kt
//│   │   └── LoginScreen.kt
//│   ├── viewmodel/                   // (Se não estiver dentro de features/)
//│   │   ├── SplashViewModel.kt
//│   │   └── LoginViewModel.kt
//│   ├── composable/                  // (Composables compartilhadas por várias features/telas)
//│   │   └── GenericLoadingIndicator.kt
//│   ├── theme/                       // Tema do aplicativo
//│   │   ├── Color.kt
//│   │   ├── Theme.kt
//│   │   └── Typography.kt
//│   └── MainActivity.kt              // Ponto de entrada principal da UI
//│
//└── util/                            // Utilitários de nível de aplicativo
//├── Constants.kt
//└── Extensions.kt