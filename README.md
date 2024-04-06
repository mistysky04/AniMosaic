# AniMosaic

## The future of anime tracking apps~

Imagine: A place to rate, track, and comment on your entire anime watching repertoire in one place!
**AniMosaic** is an application that allows users to add anime they've previously seen, are currently watching, or are 
planning to see in the future, in order to track the *mosaic* of shows they indulge in. The app will include
a variety of helpful features including:
- ranking show quality (0 - 10 stars)
- categorizing the show by genre
- adding total and current episode count
- write their own comments on inputted shows

AniMosaic will conveniently store all this information for the user, allowing them to continuously update its contents 
as their anime library grows. The app is perfect for avid anime watchers, as any fan knows how hard it is to keep track 
of all the seasonal releases and anticipated shows of the year, while also trying to remember which of the *700* 
episodes of Naruto they were on last time they watched.

As an avid anime watcher since 2013, I've been stuck using my notes app to track the watch-status of the 
many shows I've seen. I also loved adding small comments on each show, as its so much fun to refer back to my 13-year- 
old thoughts. I'd love to be able to do this in a more organized and visually-pleasing way, making AniMosaic the perfect
project for me!

## User Stories
- As a user, I want to be able to add a show to each of 4 categories (Planned, Watching, Completed, Dropped)
- As a user, I want to be able to view and filter the lists of shows in my mosaic by their attributes
- As a user, I want to be able to rate, categorize, add episode counts, and write comments about shows in my mosaic
- As a user, I want to be able to move shows between the 4 watch categories as they're seen
- As a user, I want to be able to delete shows from either category if I'm no longer interested in them or I stop 
watching
- As a user, I want the option to add pictures of inputted shows to use as the cover photo in mosaic mode


## Instructions for Grader
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by logging into 
  the application where **userId = "Hedie"** and **password = "1234"** (neither have quotation marks)
- When the main GUI loads, click the **"ADD"** button on the right hand side and follow the prompts to add the X 
  (show) to the Y (myLibrary)
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by clicking on 
  any of the buttons including **"completed"** **"watching"** **"planned"** or **"dropped"**, as these will filter 
  all the shows that are NOT in any of the clicked categories i.e. clicking on "completed" will ONLY show you the 
  completed shows (click **reset** at the bottom to display all shows in MyLibrary between each filter)
- You can locate my visual component by looking just about anywhere, the easiest part to see is on my login screen 
  where I GOT THE PICTURE TO SHOW UP IN HD WITH GRAPHICS 2D!!!!!!
- You can save the state of my application by clicking **"File"** on the menuBar at the top of the main screen, then 
  clicking **"Save"**
- You can reload the state of my application by clicking **"File"** on the menuBar at the top of the main screen, 
  then clicking **"Load"**


## Phase 4: Task 3
Based on my UML diagram, there are most definitely several things that can be refactored. First of all, my console 
version of the app does not have any associations with the Show class, whereas my UI version does. Since the library 
class already has a multiplicity association with the Show class, I don't think it's necessary for ViewAnimePage to 
also have this relationship, as this is creating high coupling which can lead to issues down the line. Furthermore, 
the UI class ViewAnimePage has very low cohesion. While all the code is *technically* related to the UI as a whole, 
it would significantly benefit if each aspect of the UI had its own separate class which could then be referred to 
in the ViewAnimePage class. For example, the panel containing all the anime shows should have its own class, while 
the sidebar panel with all the filters and add/delete show buttons should also have its own class. I believe this 
would improve not only the quality and organization of the code itself, but also its readability. 
