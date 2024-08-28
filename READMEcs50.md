# Magnum Opus
#### Video Demo:  https://www.youtube.com/watch?v=pM8rKoxoiqI
#### Description:
Alongside my time with CS50, I also worked through Google's lessons for Android development, 
and I thought that the CS50 final project was a perfect opportunity to combine what I had been 
learning in both areas. Magnum Opus is an app for guitarists that is able to 
identify chord shapes, display scales, and display voicing options for chords using a fretboard 
interface. The fretboard can be tuned to any tuning, can have a variable number of strings and 
frets, and provides contextual information about the function of the pitches. Magnum Opus is a 
Kotlin Multiplatform app, and using this framework it is able to deploy to Android, iOS, and 
desktop.
* `Academic Honesty` All of the code in the app was written by me, except for copy-and-pasted sample code from 
the official Jetpack Compose and Material documentation that obviously had to be fleshed out. I used
Material 3 for UI components and russhwolf's multiplatform-settings library to store values 
to be remembered after closing the app. The GitHub for my project shows a contributor with a few 
closed branches and commits, which was my friend and mentor helping to teach me a bit about 
how GitHub works, but these were for the sake of example and did not involve him contributing code for the app. 
He had helped me a lot while I was working through my Google lessons for Views and Compose and with the process of 
first setting up a Multiplatform project in Android Studio with appropriate configurations and 
dependencies.
* `Structure` The bulk of the code that I wrote is located in the 
`composeApp/src/commonMain/kotlin` folder, which contains 6 Kotlin files for the UI and logic of 
the pages in the app: `App.kt` `ChordIdentification.kt` `GuitarCanvas.kt` `Home.kt`
`IntervalDisplay.kt` and `Settings.kt`. It also contains a `classes` folder for the classes 
that I wrote, and a `ui.theme` folder that has some theming elements from a Material template. 
Also within `composeApp/src` there are 4 folders (`androidMain` `desktopMain` `iosMain` and 
`wasmJsMain`) which contain any code that is meant to differ between platforms. These 4 folders have little 
snippets that were created in the default Multiplatform template but not much else yet, other than 
my setting of the window size for the desktop version of the app. `commonTest` contains some 
tests that will be expanded upon in the future, and `androidMain/res` contains 
some open source icons for the Android version of the app. The rest of the contents are 
configuration and build settings from the Multiplatform template. I will go into detail about 
the 6 aforementioned Kotlin files and the classes in the `classes` folder.
* `App.kt` and the contained `App` function are the main thing within which the rest of the app 
exists. The `App` function is a Composable function, which are the UI building blocks of a 
Jetpack Compose app. These "Composables" display UI and have built in logic to decide when to 
"recompose" or essentially recalculate that UI element on the screen when contained values change. 
`App()` contains a Material AppTheme, ModalNavigationDrawer, and TopAppBar. The navigationState 
decides whether we are looking at the Home, Chord Identification, Interval Display, or Settings 
page, and the currentGuitar keeps track of other variables we need to know and remember throughout 
the app, using the Guitar class that I built.
* `ChordIdentification.kt` was the place that I started with the app, and the contained Chord 
and ChordInterpretation classes were where a bulk of my time was spent. Most of the other classes 
were written in service of the functionality within the ChordInterpretation class. 
`ChordIdentification.kt` uses the `GuitarCanvas.kt` Composable to draw a Canvas of lines and 
circles which represent the guitar fretboard UI on the screen. Tapping on a fret updates the 
currentGuitar's FretMemory with x and y values to keep track of which fret was selected, 
and displays a circle to represent the fret being selected (much later I added the note names and 
color fill to the frets based on what interval the selected note is in relation to the root of 
the chord). The selected frets are turned into Pitch class objects, and a list of these Pitch objects 
are passed into a Chord class object, which iterates over each Pitch and creates a ChordInterpretation 
class object for each one, with each ChordInterpretation essentially assuming that the Pitch is the 
root of the Chord and applying values to a relevancyScore as it calculates what each other Pitch is in 
relation to this root. The Chord then looks at the relevancyScore of each ChordInterpretation and 
sorts them so that `ChordIdentification.kt` can decide which ChordInterpretation should be selected 
as the most likely proper reading of the chord we're looking at on the fretboard. Having been a 
music composition major in my undergrad, this functionality was what I was most interested in when 
starting this project. It got more complicated than I was initially imagining, but it 
was really fun to pick apart the internal logic I had when analyzing a chord. The classes could 
definitely use some refactoring in the future, but they come packed with a lot of context, such as 
being able to take a midiValue of 29 and be able to know whether that frequency is an F note 
and the major third within a Db chord, or an E# note and the augmented fifth within a A 
augmented chord. ChordInterpretation has a big branching if/else statement in the init block within which it looks at 
the most important intervals that determine the identity of a chord and categorizes the interpretation's pitches 
in reference to the root, divorced from how they might be read. Having the pitches' frequencies correspond 
to MIDI values, determining how many semitones these are from the root, and determining whether 
a chord is major, minor, diminished, etc. precedes any possible reading of the values as musical 
notes on a sheet of staff paper, since the root note might have an ambiguous reading (such as being an 
Ab or the enharmonic G#, which both have the same frequency within a 12 tone equal temperament 
system). So, as we go down this if/else statement, we look to see what intervals this chord has 
and figure out the quality of the chord (major, minor, diminished, etc), we update the intervals 
ChordInterpretationHelper if the interval differs from the most common default (ex: 6 semitones 
can be an augmented fourth or a diminished fifth), we use the applyRelevancy function to add to the 
ChordInterpretation's relevancyScore, and we use the applyExtensions function once we know how 
to interpret the notes that aren't essential to the quality of the chord. After all this is done, 
we know enough to determine the reading for this ChordInterpretation. The Pitch class takes in a midiValue 
parameter, and from that midiValue it can determine a naturalReading, sharpReading, and flatReading, 
each being a PitchSpelling that contains a chromaticValue corresponding to the "pitch class" (a pitch without 
context of octave), a PitchLetter (A,B,C,D,E,F,G), and an Accidental (flat, sharp, natural, etc.). 
If the ChordInterpretation's root has a possible naturalReading, then we already have enough information for each Pitch's 
chosenReading to be updated to one of the three readings. If the ChordInterpretation's root does not have a 
possible naturalReading, then we have to decide between a flatReading and a sharpReading. 
To accomplish this, we create a flatRoot and a sharpRoot, and we essentially create a version 
of the ChordInterpretation's pitches for each, then tally how many flats or sharps are in both 
to see whether one or the other is easier to read. Once we know how to interpret the root, then 
we have now enough information to update the chosenReading for the rest of the notes and determine 
the name of the ChordInterpretation. 
* Other than general refactoring, the relevancyScore is the main functional thing that could use some 
tweaking. Choosing between the ChordInterpretations is a subjective thing that is hard to 
quantify, depending on contextual factors such as how many of each pitch there are and how high 
or low their frequency is in relation to others. The values I have 
chosen are kind of arbitrary but very often result in the interpretation that I would personally 
choose for most chords. I will eventually add more tests in order to further fine tune these values. 
One good illustration of the ambiguity that is possible is looking at an Am7 and a C6. 
Both of these chords have the exact same pitches within them, but depending on musical context and 
how the chord is voiced, A or C might feel stronger as the root, and this changes the function 
of all the other pitches in the chord. `ChordIdentification.kt` also has an "Alternative Readings" 
button with which you can view and select other possible interpretations. 
* I also considered how in the previous Am7 and C6 example, a classical musician analyzing a Bach 
piece would be much more likely to interpret something as an Am7 in first inversion rather than 
a C6. The way that chords function has expanded over time and through different traditions, 
and modern chord symbols are in some ways an internally inconsistent language that has 
evolved for ease of communication. There are letters and shapes and symbols that can be used to mean 
the same thing (a triangle referring to a delta chord which is synonymous with a major seventh, for example). 
The notation of a "sus4" has one metaphorical foot in the idea of a 
classical "suspension", which carries an implication of the melodic function of that note in 
a way that chafes against the idea of modern chord notation being strictly vertical. 
A lot of notes that would be interpreted as "non-chord tones" in classical theory have a place 
in modern interpretations, and others that have less of a place still want to be accounted for 
in some way in an app like this instead of simply discarded or throwing some kind of error. Slash chords 
are an exception to how modern chord notation is generally agnostic to the voicing of the chord, 
in addition to how 13 chords and add9 chords could possibly imply a higher octave (although my understanding 
is that these are generally not meant to imply voicing or octave in most jazz contexts). 
Some alterations and omissions in a 13 chord are implied or open to interpretation in a jazz 
context, although the literal interpretation of it would be that 1, 3, 5, b7, 9, 11, 
and 13 are all present. I would be interested in possibly opening the can of worms of implementing 
slash chords into my app at some point, but the compromise I have made for now is this: I have tried to opt 
for clean, concise, and consistent modern notation, using symbols when they don't feel jazz-specific 
(using "+" for augmented and "°" for diminished, but using "m" instead of "-" and "maj7" instead of 
∆), and I have opted for chord interpretations that are voicing agnostic and as literal as possible 
while allowing for omissions if enough of the chord's identity is being implied by the sonority 
(a 13 chord with a #11 would be explicit about the alteration, a chord with only a 1, 3, b7, and 
13 would still be interpreted as a 13 chord, and the octave of the 6th is not considered, so any 
6th will qualify as a 13th).
* `Home.kt` isn't much to write home about, the page describes the app and it's different functionalities. 
At some point in the future I might want to display any necessary helpful information in selected 
popups, and rethink the ModalNavigationDrawer or add more functionalities.
* `IntervalDisplay.kt` uses the classes that were developed for `ChordIdentification.kt` and instead 
takes a root note as input, and displays that pitch class in every octave across the fretboard, 
along with any other chosen intervals that you can toggle. This allows you to view scales or view 
all the possible notes that are in a chord to help make voicing decisions, or help you adventure into a new weird
tuning. Developing this functionality necessitated me adding a rootAlreadyHasReading variable to 
the ChordInterpretation class, to allow the user to choose an A# or a Bb and have it reflected 
on the fretboard, rather than have the ChordInterpretation class decide which one would be 
cleaner to look at. The pitch names on the fretboard are determined by creating a 
ChordInterpretation with the selected root. There could be an argument to split up this 
functionality into a scales option and a chord option, as the note names are currently 
determined by their function within all selected intervals as a chord. Looking at the 
chromatic scale can result in a triple sharp in one situation (A# with all intervals selected), 
which is subjective whether or not this best describes the function of the note.
* `Settings.kt` allows you to set up your Guitar to have between 4 and 8 strings, change the 
tuning of each string, and adjust the number of frets that are displayed by `GuitarCanvas.kt`. The 
choices that you make are stored on your device using russhwolf's multiplatform-settings library, 
so you can also set your start screen to whichever page you want and retain the guitar setup that 
you've chosen. If changing the number of frets results in a FretMemory being off the GuitarCanvas, 
it is reset to default in order to avoid a crash.