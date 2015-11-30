# recommendation_web_backend

## Table of Contents
- [Running](#running)
- [Development](#development)
- [Tech Stack](#tech-stack)

## Running
- Run the local activator script
  - For Linux/ Mac OS X: `./activator run`
  - For Windows: `./activator.bat run`
    - First time runs will cause dependencies to download which will take awhile.
- Navigate to `localhost:9000`
  - It will compile for the first time (will take some time), create database tables, and run.
  - The website will be visible in the browser 


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

