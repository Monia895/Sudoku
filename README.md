# Sudoku

Desktopowa gra Sudoku napisana w Javie z interfejsem graficznym Swing.

## Opis

Aplikacja oferuje dwie wersje gry dostępne z ekranu startowego:

**Wersja klasyczna** — podstawowa wersja gry z zestawem gotowych plansz.

**Wersja rozszerzona** — rozbudowana wersja z generatorem plansz i dodatkowymi funkcjami.

## Funkcje

### Wersja klasyczna
- Plansza 9x9 z podziałem na kwadraty 3x3
- Walidacja w czasie rzeczywistym, błędna cyfra podświetla się na czerwono
- Timer i licznik błędów
- Przycisk Nowa gra i Resetuj

### Wersja rozszerzona
- Generator losowych plansz oparty na algorytmie backtracking
- Trzy poziomy trudności: łatwy, średni, trudny
- Podświetlanie cyfr, kliknięcie cyfry zaznacza wszystkie takie same na planszy
- System podpowiedzi (3 na grę)
- Zapis i odczyt stanu gry do pliku
- Ekran wygranej ze statystykami i zapisem najlepszych czasów
- Animacja wygranej

## Struktura projektu
```text
src/
├── sudoku/
│   ├── model/          # Cell, Board
│   ├── game/           # GameState, GameSaver, BestTimes
│   ├── logic/          # Solver, Generator, Difficulty
│   └── ui/             # MainWindow, MainWindowV2, BoardPanel, MenuWindow, GameEventListener, LimitDocument, WinDialog
└── Main.java
test/
└── sudoku/
├── model/          # BoardTest
└── logic/          # SolverTest
