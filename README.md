# Gears Project

This is a Google App Engine project migrated from Eclipse to VS Code.

## Getting Started with VS Code

### 1. Install Recommended Extensions
When you open this project in VS Code, you should see a prompt to install recommended extensions. If not, go to the Extensions view (`Ctrl+Shift+X`) and search for:
- **Extension Pack for Java** (Microsoft)
- **Google Cloud Code** (Google)

### 2. Configure Java
This project uses **Java 8**. Ensure you have a Java 8 JDK installed.
You can configure the JDK in VS Code settings:
- `java.configuration.runtimes`: Add your Java 8 path here.

### 3. Running the Project
You can run the App Engine development server using the VS Code Task:
- Press `Ctrl+Shift+P` -> `Tasks: Run Task` -> `appengine: run`.
- Or use the terminal: `mvn appengine:run`

### 4. Debugging
- Use the **Debug AppEngine** configuration in the Run and Debug view (`Ctrl+Shift+D`).
- This will run the `appengine: run` task and attach the debugger.

### 5. Deploying
- Run the task `appengine: deploy`.
- Or use the terminal: `mvn appengine:deploy`

## Project Structure
- `src/main/java`: Java source code.
- `src/main/webapp`: Web resources (HTML, CSS, JS) and App Engine configuration.
- `pom.xml`: Maven configuration.
- `.vscode/`: VS Code specific settings.
