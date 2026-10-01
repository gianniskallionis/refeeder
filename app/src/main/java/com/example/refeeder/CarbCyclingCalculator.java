package com.example.refeeder;

public class CarbCyclingCalculator {
    public static class Result {
        public String dayType;
        public double calories;
        public double protein;
        public double carbs;
        public double fats;
    }

    public static Result calculate(
            int age,
            double weight,
            double height,
            String sex,
            String activityLevel,
            String trainingType
    ) {
        Result result = new Result();

        double bmr;

        if (sex != null && sex.equalsIgnoreCase("male")) {
            bmr = 10 * weight + 6.25 * height - 5 * age + 5;
        } else {
            bmr = 10 * weight + 6.25 * height - 5 * age - 161;
        }

        double activityFactor = getActivityFactor(activityLevel);
        double tdee = bmr * activityFactor;


        double dayCalories;
        double fatMultiplier;

        if (trainingType != null && trainingType.equalsIgnoreCase("both") ) {
            result.dayType = "High Carb Day";
            dayCalories = tdee * 1.15;
            fatMultiplier = 0.8;}
        else if(trainingType != null && trainingType.equalsIgnoreCase("weights")){
            result.dayType="High Carb Day";
            dayCalories=tdee*1.1;
            fatMultiplier=0.8;}

         else if (trainingType != null && trainingType.equalsIgnoreCase("cardio")) {
            result.dayType = "Medium Carb Day";
            dayCalories = tdee*1.05;
            fatMultiplier = 0.8;
        } else {
            result.dayType = "Low Carb Day";
            dayCalories = tdee * 0.85;
            fatMultiplier = 1.0;
        }

        double proteinGrams = weight * 2.2;
        double fatGrams = weight * fatMultiplier;

        double proteinCalories = proteinGrams * 4;
        double fatCalories = fatGrams * 9;

        double carbCalories = dayCalories - proteinCalories - fatCalories;
        double carbGrams = carbCalories / 4;

        if (carbGrams < 30) {
            carbGrams = 30;
        }

        result.calories = Math.round(dayCalories);
        result.protein = Math.round(proteinGrams);
        result.carbs = Math.round(carbGrams);
        result.fats = Math.round(fatGrams);

        return result;
    }

    private static double getActivityFactor(String activityLevel) {
        if (activityLevel == null) {
            return 1.2;
        }

        switch (activityLevel.toLowerCase()) {
            case "low":
            case "sedentary":
                return 1.2;

            case "light":
                return 1.375;

            case "medium":
            case "moderate":
                return 1.55;

            case "high":
                return 1.725;

            case "very high":
                return 1.9;

            default:
                return 1.2;
        }
    }
}
