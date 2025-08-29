# CreepyAtlas

## Requirements

- **Java 17** or higher  
- **Maven 3.6+**  
- **Git**
- **Git Large Files**
## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/creepyatlas.git
   cd creepyatlas
   ```

2. Make sure you have Java installed:
   ```bash
   java -version
   ```
   Should return something like `17.x`.

3. Make sure you have Maven installed:
   ```bash
   mvn -version
   ```

## Running the Project

1. Compile and run the project with Maven:
   ```bash
   mvn clean compile exec:java
   ```

If everything is configured correctly, the `.wav` file should play through OpenAL.
