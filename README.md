# Refeeder 

An Android app that calculates daily macros for **carb cycling**, built with Java and Android Studio.



## What it does

- Takes the user's personal info (e.g. body data and goal) and calculates a daily macro plan
- Applies a carb cycling logic to the calculations, on days that you are resting you eat lower carbs, on days that you
- lift weights or do cardio you eat a medium amount of carbs and on days when you do both you eat high carbs for best 
- performance whilst limiting the calorie intake.
- Shows the resulting macros on a dedicated output screen
- Saves user profiles locally and lists them in a scrollable list

## Tech stack

| Area | Tools |
|---|---|
| Language | Java, XML |
| UI | Material Components, RecyclerView |
| Local storage | Room (SQLite) |
| Build | Gradle (Kotlin DSL) |
| IDE | Android Studio |

## Project structure

```
app/src/main/java/com/example/refeeder/
├── MainActivity.java            # entry screen
├── Personal_Info.java           # personal info input
├── CarbCyclingCalculator.java   # macro calculation logic
├── MacrosOutputActivity.java    # results screen
├── ProfilesActivity.java        # saved profiles
├── ProfileAdapter.java          # RecyclerView adapter
└── data/
    ├── db/                      # Room database and DAOs
    └── entity/                  # User and DailyPlan entities
```

## Run it

1. Clone the repository
2. Open it in Android Studio and wait for the Gradle sync
3. Run on an emulator or a connected Android device


## Team
| Member | Role |
|---|---|
| [gianniskallionis](https://github.com/gianniskallionis) | UI, backend logic |
| [tdaska824](https://github.com/tdaska824) | Backend logic |
| [Nik3p](https://github.com/Nik3p) | Database |