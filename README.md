# COE891: jEdit Search & Replace Testing Project

This project contains a comprehensive Java testing environment for the `org.gjt.sp.jedit.search` package from the open-source jEdit text editor. It covers multiple testing abstractions (ISP, Logic-based, Graph-based, Mutation testing) and includes an LLM-Assisted Component generated via ChatUniTest.

## Prerequisites
Before running the project, ensure you have the following installed on your system:
- **Java JDK 11** (or higher)
- **Apache Maven 3.6+**
- **LM Studio** or **Ollama** (Only necessary if you intend to re-trigger the ChatUniTest LLM test generator).

## Environment Setup
1. Clone or extract this `COE891_Tests` folder onto your local machine.
2. Ensure that the original `jedit.jar` file is located inside the `lib` directory within the COE891_Tests folder (e.g., `COE891_Tests/lib/jedit.jar`). The `pom.xml` expects the dependency to live at `${project.basedir}/lib/jedit.jar`.

---

## 🚀 Running the Test Suite (JUnit 5)
To compile the test classes and execute all 20 manual and LLM-generated tests, open your terminal in the `COE891_Tests` directory and run:

```bash
mvn clean test
```
*Note: The `maven-compiler-plugin` has been specifically configured to exclude `src/main/java` so that it doesn't overwrite the original `jedit.jar` bytecode. It will execute perfectly.*

---

## 📊 Generating JaCoCo Code Coverage
To generate the Instruction, Branch, and Line coverage HTML reports:

```bash
mvn test jacoco:report
```
Once the build passes, navigate to `target/site/jacoco/index.html` in your web browser to view the highly detailed line-by-line coverage for the `org.gjt.sp.jedit.search` package.

---

## 🦠 Running PIT Mutation Testing
To dynamically inject faults (mutants) into the `jEdit` source bytecode and evaluate how robust our test suites are at "killing" them, run:

```bash
mvn pitest:mutationCoverage
```
Once the build completes, the overall Mutation Score report will be accessible at `target/pit-reports/index.html`.

---

## 🤖 (Optional) Re-running ChatUniTest LLM Generation
We have already successfully generated, validated, and integrated the tests using the `qwen3-coder-30b` LLM model. However, if you wish to re-test the ChatUniTest Maven plugin:

1. **Start Local Server:** Open LM Studio and start the local server on `http://127.0.0.1:1234/v1` (or adjust the URL in `pom.xml` if using Ollama).
2. **Generate Tests:** Run the generation command, defining which class the LLM should target:
   ```bash
   mvn chatunitest:class -DselectClass=org.gjt.sp.jedit.search.BoyerMooreSearchMatcher
   ```
3. **Copy to Test Directory:** Once the generation finishes successfully, copy the test code over to your `src/test/java` directory by running:
   ```bash
   mvn chatunitest:copy
   ```

## Key Technologies Used
*   **JUnit 5 (Jupiter)** - Primary testing framework
*   **Mockito 5 (`mockito-inline`)** - Headless GUI (`SearchDialog`) manipulation and dependency injection
*   **JaCoCo Maven Plugin** - Code coverage
*   **PIT (`pitest-maven`)** - Mutation coverage
*   **ChatUniTest** - LLM-Assistant Integration

