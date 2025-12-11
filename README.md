# Data-Visualization
This project is an application that loads data and shows it through clear, simple visualizations. Users can explore patterns, compare values, and interact with the data using built-in tools.

MVC Structure

The program is built using the Model-View-Controller (MVC) pattern:

Model:
The model contains all data and logic. Each data structure is its own class and implements Serializable so the program’s state can be saved and loaded.
The model includes:

Stack – used for storing recent actions

Queue – used for managing tasks or processing order

Linked List – used to hold and update the main data set

Insertion Sort – used to sort data in small or nearly-sorted lists

Bubble Sort – used for simple, step-by-step visual sorting
Each structure has its own class with the necessary functions built directly into the model.

View:
Displays charts, data, and any user interface elements.

Controller:
Handles button clicks, passes data between the model and view, and updates the display.

Development Process

This project was created by [Team Member Names Here] using Agile Scrum.
We worked in weekly sprints, met to plan and review progress, and split tasks so each member contributed to design, coding, and testing.

Features

Interactive data visualizations

Multiple sorting method options

Stack, queue, and linked list structures

Save/load support through serializable model classes

Clear, simple MVC design
