

**AudioSpeakerDiarization**
============================================================
Kang is glad you find me. This is an android project for speaker diarization by speech with SVM classification method. The recognition process is of high efficiency

##Note

A simple android app for real-time speaker diarization. As far as I'm concerned, there is no real-time speaker diarizatino tool now.
My last project uses the simplest method, i.e. LPC + MFCC / Euclidean Distance. And then I develop an android demo using i-vector with tool LIUM. However, the time efficiency is too low to satisfy my demand.

##Usage

You can use this app by the following two steps.
  1. Process of creating user. Record a clip of audio recording ( >= 10 seconds is good, longer is better). I have realized the recording function in the app. You should push the 1st button to start recording and push the 3rd button to end it. And after you push the 3rd button, PLEASE WAIT FOR A LONG TIME!
  2. Process of recognition. Push the 1st button to start and push the 2nd button to end it. And while you push the 2nd button, the recognition results will be shown in the display board.

##Methods
  Feature: LPC(15d) + MFCC(13d + 3d).

  Frame length: 46ms, overlap 23ms, the basic unit for extracting features.

  Segment length: 400ms, overlap 200ms, the length of training and testing instance.

  Negtive instances: A collection of recordings with various speakers and backgrounds. Then they are divided into clips with length of 400ms.

  Positive instances: Online recording from Step 1. Then they it is divided into clips with length of 400ms.

  Testing instance: Online recording from Step 2. Then it is divided into clips with length of 400ms.

  Classification: SVR with linear kernel.

##For Developers.
  1. MainAcitivity.java is at /src/com/sidv50/MainActivity.java.
  2. You can also get to start on this project by reference to /src/aspt/BasicFE.java.
