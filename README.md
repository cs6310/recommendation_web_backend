# recommendation_web_backend

## Table of Contents
- [Running](#running)
- [Using](#using)
- [Development](#development)
- [Tech Stack](#tech-stack)
- [Source code](#source-code)

## Running
- Get a gurobi license and download gurobi.jar and place in lib folder: [Gurobi](https://www.gurobi.com/index)
- Run the local activator script
  - For Linux/ Mac OS X: `./activator run`
  - For Windows: `./activator.bat run`
    - First time runs will cause dependencies to download which will take awhile.
- Navigate to `localhost:9000/login`
  - It will compile for the first time (will take some time), create database tables, and run.
  - The website will be visible in the browser

## Using
### Student
- Logging in as a Student (All ids are numerical)
  - Pick an id between 1 and 600, inclusive.
  - Type the password `12345`
- Pick a semester.
- Pick your course preferences for the semester.
  - Select the number of courses you want.
  - Prioritize the courses you want. (1 being highest priority)
  - Submit the Request.
- On the next page, you view your courses.
  - View the courses for the semester
  - View the courses for all the semesters.

### Administrator
- Logging in as an Administrator (All ids are numerical)
  - Pick an id greater than 1000.
  - Type the password `12345`
- Upload input files(A total of 3 files are needed. FIles are provided under the conf/resourses directory)
  - Each file has specific formatting requirements and are named according the information they contain.
  - Submit the request
- On the next page, the information from the files is displayed.

## Development
### Working in Eclipse (One-time setup per developer)

#### Step 1. Importing the Play Project Correctly
- Clone the project via command-line
- cd into the root of the project.
- Run `./activator eclipse` to generate the eclipse projects file locally on your computer.
- Open Eclipse
- In the menu: `File/Import/General/Existing project` and navigate to the root folder of project.
- You should see a project called `recommendation-web-backend`. Make sure it is checked and click `Finish`.

#### Step 2. Connecting the imported project to Git
- Go to `Window/Show View/Other/Git/Git Repositories'
- Click the Add Git Repository button (has a green star on the button)
- Browse to the directory where the project is.
- It should find the Git repo in there _if it hasn't been added before_
- Press `Finish`
- Right-click on the project in the `Project Explorer` view. Click ` Team/Share Project..`
- Check the `Use or create repository in parent folder of project`
- Click the box in the row where the `Repository` value is `.git`
- Click `Finish`

### Tech Stack

This project uses:
- [Play Framework](https://www.playframework.com/)
- H2 In-Memory Database

### Source Code

You can view our source code on [Github](https://github.com/cs6310/recommendation_web_backend)
