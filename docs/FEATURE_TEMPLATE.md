# New feature template

1. Branch: `feat/short-name` (never `master`).
2. Module `:feature:<name>` with Screen + ViewModel + state + fake.
3. Koin `viewModel { }` in `portfolioModule` (or a feature module).
4. Destination in `PortfolioFeature` + catalog item in `PortfolioCatalog`.
5. Unit test for the ViewModel / use case.
6. Compose UI test tagged on the root.
7. README in the module (one paragraph: what a recruiter should notice).
8. Stop and let the author review the diff before committing.
