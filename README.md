

**AudioSpeakerDiarization**
============================================================
An android project for speaker diarization by speech with SVM classification method

##Note

A simple android app for real-time speaker diarization. As far as I'm concerned, there is no real-time speaker diarizatino tool now.
My last project uses the simplest method, i.e. LPC + MFCC / Euclidean Distance. And then I develop an android demo using i-vector with tool LIUM. However, the time efficiency is too low to satisfy my demand.

##Usage

You can use this app by the following three steps.
  1. Process of creating user. Record a clip of audio recording ( >= 10 seconds is good). I have realized the recording function in the app. You should push the 1st button to start recording and push the 3rd button to end it. And after you push the 3rd button, PLEASE WAIT FOR A LONG TIME!
  2. Process of recognition. Push the 1st button to start and push the 2nd button to end it. And while you push the 2nd button, the recognition results will be shown in the display board.

##For Developers.
  1. MainAcitivity.java is at /src/com/sidv50/MainActivity.java.
  2. You can also get to start on this project by reference to /src/aspt/BasicFE.java.
