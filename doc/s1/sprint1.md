The goal of this sprint is to begin implementing features that are labelled as User Stories using specific design patterns in Java as well as ones taught in lectures.

Current Assessment of Team Capacity: We expect to complete most of the design patterns and finish most of the features except for a few (e.g. exit feature). Also, want to add more design patterns moving from sprint 1 to sprint 2 creating a more customizable gameplay experience.

All stories for this sprint: User story 1.0, 1.1, 1.2, 1.3, 1.4, 1.5

Participants in Sprint 1:

Guy:

Expectations: Assigned User Story 1.0 move log history viewer, and 2.1, best possible moves. Need to create a textfile to put moves with DateTime so players can see the game after being played. Create an outline for the singleton pattern for board copy and mediator pattern (move log). Aiming to complete the move log history with Piyush doing the create and remove functions. The next step is to implement the best possible move feature for humans to view as a hint in the game using the minmax algorithm.

Breakdown of tasks completed: Fully implements the singleton and mediator pattern to be used for both User Stories. For the move log, a text file is created correctly. Singleton pattern and mediator pattern are correctly implemented without any issues.

Piyush:

Expectations: Assigned User Story 2.0 save feature and 2.2, Exit feature, and implement functions for User Story 1.1 which adds and removes moves from the move log in a stylistic fashion. Implement a save feature for the game which allows users to come back to the game from where it was left off. The next step is to implement the exit feature for the game. Complete User Story 2.0 save feature.

Breakdown of tasks completed: Completed save feature to allow the saving board and the current moves done. Complete the mediator pattern by finishing the move/remove functions from the move log which appends moves to the end of the text file. Starting to work on an exit feature for the game to allow users to exit without a real winner.

Guy/Piyush:

Implement skip turn of the game which is User Story 1.3. Expect to be done this quite fast. Both completed User Story 1.0 (move log) and 1.3 (skip turn) together by splitting up the work.

Maryum:

Expectations: Assigned User Story 1.1 (update move log), 1.2 (show previous moves), and 1.5 (custom pieces) with Ayesha. Also aims to implement the factory pattern and strategy pattern from the design patterns that we came up with as a group. The factory pattern will be used to create pieces and the strategy pattern will be used to refactor the Agent class and subclasses. The expectation is to complete the design patterns and start the user stories. Problems with user story 1.2 and 1.5 are expected as it deals with user input and there is a lot of error handling to be done.

Breakdown of tasks completed: The factory pattern and strategy pattern are implemented with minimal issues. The user story 1.2 has also been implemented but there needs to be more testing for bugs. User story 1.5 has been completed successfully and fully tested for bugs. The next steps are to refactor the rest of the code and make sure the other classes are using the updated design patterns and also to fix bugs in the move log.

Ayesha:

Expectations: Assigned User Story 1.4 (randomize board) and 1.5 (custom pieces) with Maryum. Implement the builder pattern and make working features for stories 1.4 and 1.5. The expectation is to finish 1.4 (individually) and 1.5 with Maryum in sprint 1 and to look for any existing bugs.

Breakdown of tasks completed: The builder pattern is implemented and any existing bugs were fixed. Stories 1.2 and 1.5 were completed and bugs in both were fixed. The next steps are to test the game and to fix any other existing bugs.

Ayesha/Maryum:

Expectations: Implement user story 1.5 which allows users to choose custom pieces for the Musketeer and Guard players. Create a new method that gets input from the user. Expect to run into some problems with error handling and parsing input.

Breakdown of tasks completed: Completed user story 1.5 with some bugs. Bug 1: we could not update the board properly. Fixed by changing the Board constructor. Bug 2: found when the user played against the GreedyAgent. Fixed by changing CellBuilder so it adds the correct pieces to the copy of the Board.
