package com.anton;

import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "classpath:features",
        glue = {"steps", "hooks"}
)
public class RunnerTest {
}
