1. Clone project.
2. CD into the project directory 'sudoku-validator'
3. Build with 'mvn clean install'
4. CD into 'distribution' directory.
5. unzip distribution.zip.
6. Use .bat or .sh file in terminal :
  WIN
  validate.bat examples/example1.txt examples/example2.txt examples/example3.txt
  LINUX/MACOS
  chmod +x ./validate.sh
  ./validate.sh examples/example1.txt examples/example2.txt examples/example3.txt
7. CD to 'reports' directory and open 'index.html' with browser for test coverage report.
