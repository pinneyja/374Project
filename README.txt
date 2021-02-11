Project Structure:
    - resources/                # Contains input files and output files.
        - out/                      # Contains output files created upon program execution
            - App-response.json         # Contains the generated JSON app responses
            - Command_stream.json       # Contains the generated JSON commands
        - controller-response.json  # Input file for controller's responses
        - order-input.json          # Input file for orders
        - db.json                   # Input file for database
    - src/                      # Contains all source code organized into layers
        - Service Layer/            # Contains files relating to the Service Layer
        - Business Layer/           # Contains files relating to the Business Layer
        - Data Layer/               # Contains files relating to the Data Layer
        - Helpers/                  # Contains helpful methods for reading from files.
        - Test/                     # Contains test files
        - Main.java                 # Main file that runs the application
    - lib                       # Contains all external libraries used
        - json-20201115.jar         # Used to read JSON files

How to Run:
    1. Mark src/ and resources/ as Sources Root and Resources, respectively.
    2. Mark lib/json-20201115.jar as a library.
    3. Set Main.java as the file to be run.
    4. Run the project.
    5. The output files will be located in resources/out/.

How to Run Tests:
    1. Complete steps 1-2 in "How to Run".
    2. Set Test/DunkinTest as the file to be run.
    3. Run the project.
    4. The output files will be located in resources/out/ but may not be complete due to the granularity of the tests.
       The tests passing / failing should be the primary indicator of success.

Possible Errors:
    1. Could not find files.
        - Potential Fix:
            Mark the resources/ folder as Resources.
        - Potential Fatal Error:
            This may occur if you are on an untested OS. This may be a side effect of our src/Helpers/Utilities.java
            class hardcoding expected paths based on the project structure. It has been verified to work on all the
            team's Windows machines, but is not tested for any other OS / file systems.
    2. JSON error
        - Potential Fix:
            Check the input files for JSON errors. These must be valid JSON files.
    3. Could not resolve JSON methods.
        - Potential Fix:
            Mark the lib/json-20201115.jar file as a library.