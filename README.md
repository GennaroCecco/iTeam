<div style="text-align: center;"><img alt="https://github.com/GennaroCecco/iTeam/blob/development/src/main/webapp/img/FIAntastici4.png?raw=true" src="https://github.com/GennaroCecco/iTeam/blob/development/src/main/webapp/img/FIAntastici4.png?raw=true"></div>

# iTeam

iTeam is a project for FIA (**Fondamenti di Intelligenza Artificiale** that stands for *Foundaments of Artificial Intelligence*), a course of the Department of Computer Science of the Università di Salerno, that deals with the creation of an artificial intelligence, capable of creating teams of employees
based on the requirements of the project that has to be carried out.
This project is built upon [Agency Formation](https://github.com/SystemFormation/AgencyFormation).

## Developers

* **Gennaro Cecco** *Human Torch* - [Gennaro Cecco](https://github.com/GennaroCecco)
* **Luigi Giacchetti** *Mister Fantastic* - [Luigi Giacchetti](https://github.com/Rankoll)
* **Vincenzo Liguori** *Thing* - [Vincenzo Liguori](https://github.com/vliguori99)
* **Emanuele Scarpa** *Invisible (Wo)man* - [Emanuele Scarpa](https://github.com/ManuScarpa)

## Documentation

The entire documentation of the project can be found in *doc/iTeam_Documentazione.pdf*.

## Installation

All installation steps are written in *doc/iTeam_MDI.pdf*.

## Clone and run the project in localhost
You can follow these steps or installation manual.
1. Clone this repo: `git clone https://github.com/GennaroCecco/iTeam.git`
2. Run `gradlew war` and wait for gradle build.
3. Inside directory build/libs you can find .war file.
4. Deploy .war file on your java server(ex. Tomcat).
5. Import database and populate it with the script that you can find in **folder AF_DB**.
6. Open your browser on `localhost:8080\iTeam`.
7. Login with ["m.nocerino@studenti.unisa.it"](mailto:m.nocerino5@studenti.unisa.it) password: **"lol"** for Team Manager.
8. Click on button "Lista Team" and then click on the button "Ottimizza con la nostra AI".
9. Enjoy.

### How to run AI
1. Open doc/iTeam_MDI.pdf.
2. Go to chapter 5 and follow the step-by-step guide from there.

### Branches

* Master branch contains the releases of features that have been tested and approved.
* Development branch contains various unstable features.

### Get new data from my branch
If you and another *developer* are working on the same feature branch but you want to get the changes he pushed:

1. Make sure you are in YOUR (already existing) feature branch. If not: `git checkout development`
2. `git pull`
3. Resolve possible conflicts. Ask if you are in trouble. A common solution is:
* `git stash` to save your local changes in a local secure stack
* `git pull`
* `git stash pop` to reapply your local changes again