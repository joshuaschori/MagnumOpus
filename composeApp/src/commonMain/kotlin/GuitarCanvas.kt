import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import classes.Guitar
import classes.IntervalColor
import ui.theme.defaultInterval0ColorBlue
import ui.theme.defaultInterval0ColorGreen
import ui.theme.defaultInterval0ColorRed
import ui.theme.defaultInterval10ColorBlue
import ui.theme.defaultInterval10ColorGreen
import ui.theme.defaultInterval10ColorRed
import ui.theme.defaultInterval11ColorBlue
import ui.theme.defaultInterval11ColorGreen
import ui.theme.defaultInterval11ColorRed
import ui.theme.defaultInterval1ColorBlue
import ui.theme.defaultInterval1ColorGreen
import ui.theme.defaultInterval1ColorRed
import ui.theme.defaultInterval2ColorBlue
import ui.theme.defaultInterval2ColorGreen
import ui.theme.defaultInterval2ColorRed
import ui.theme.defaultInterval3ColorBlue
import ui.theme.defaultInterval3ColorGreen
import ui.theme.defaultInterval3ColorRed
import ui.theme.defaultInterval4ColorBlue
import ui.theme.defaultInterval4ColorGreen
import ui.theme.defaultInterval4ColorRed
import ui.theme.defaultInterval5ColorBlue
import ui.theme.defaultInterval5ColorGreen
import ui.theme.defaultInterval5ColorRed
import ui.theme.defaultInterval6ColorBlue
import ui.theme.defaultInterval6ColorGreen
import ui.theme.defaultInterval6ColorRed
import ui.theme.defaultInterval7ColorBlue
import ui.theme.defaultInterval7ColorGreen
import ui.theme.defaultInterval7ColorRed
import ui.theme.defaultInterval8ColorBlue
import ui.theme.defaultInterval8ColorGreen
import ui.theme.defaultInterval8ColorRed
import ui.theme.defaultInterval9ColorBlue
import ui.theme.defaultInterval9ColorGreen
import ui.theme.defaultInterval9ColorRed
import ui.theme.defaultIntervalColorAlpha

@Composable
fun GuitarCanvas(
    navigationState: String,
    innerPadding: PaddingValues,
    currentGuitar: Guitar,
    onGuitarChange: (Guitar) -> Unit
) {
    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier = Modifier
        // TODO insetHorizontal * 2.5 is an arbitrary fix, so note names with accidental don't overflow
        .height((insetVertical * 4 + currentGuitar.fretSpacing * settings.getInt("number of frets", 15)).dp)
        .width((insetHorizontal * 2.5 + currentGuitar.stringSpacing * ( settings.getInt("number of strings", 6) - 1)).dp)
        .padding(top = insetVertical.dp, bottom = innerPadding.calculateBottomPadding())
        .pointerInput(Unit) {
            detectTapGestures(
                // update fretMemory if the coordinates of a fret are clicked
                onPress = {

                    if (navigationState == "Chord Identification") {
                        val pointerX = it.x
                        val pointerY = it.y

                        // determine if you have clicked within range of a string
                        for ((stringIndex, string) in currentGuitar.stringLocations.withIndex()) {
                            if (string.dp.toPx() in pointerX - insetHorizontal.dp.toPx() - currentGuitar.fretSelectionRadius.dp.toPx()
                                ..pointerX - insetHorizontal.dp.toPx() + currentGuitar.fretSelectionRadius.dp.toPx()) {
                                // determine if you have clicked within range of a fret
                                for ((fretIndex, fret) in currentGuitar.fretLocations.withIndex()) {
                                    if (fret.dp.toPx() in pointerY - insetVertical.dp.toPx() - currentGuitar.fretSelectionRadius.dp.toPx()
                                        ..pointerY - insetVertical.dp.toPx() + currentGuitar.fretSelectionRadius.dp.toPx()) {
                                        // if you have clicked on a fret that is already selected, then deselect it
                                        if (
                                            currentGuitar.fretMemory[stringIndex].y == fret && currentGuitar.fretMemory[stringIndex].visible
                                        ) {
                                            currentGuitar.fretMemory[stringIndex].x = currentGuitar.stringSpacing * stringIndex
                                            currentGuitar.fretMemory[stringIndex].y = 0f - currentGuitar.fretSpacing * 0.5f
                                            currentGuitar.fretMemory[stringIndex].fretSelected = 0
                                            currentGuitar.fretMemory[stringIndex].visible = false
                                        }
                                        // else store the coordinates of the newly selected fret in fret memory
                                        else {
                                            currentGuitar.fretMemory[stringIndex].x = currentGuitar.stringSpacing * stringIndex
                                            currentGuitar.fretMemory[stringIndex].y = fret
                                            currentGuitar.fretMemory[stringIndex].fretSelected = fretIndex
                                            currentGuitar.fretMemory[stringIndex].visible = true
                                        }
                                    }
                                }
                            }
                        }
                        val newGuitar: Guitar = currentGuitar.copy(fretMemory = currentGuitar.fretMemory.toMutableList())
                        onGuitarChange.invoke(newGuitar)
                    }
                }
            )
        }
    ) {
        inset(horizontal = insetHorizontal.dp.toPx(), vertical = insetVertical.dp.toPx()) {
            // draw the guitar strings
            repeat(currentGuitar.numberOfStrings) { stringIndex ->
                drawLine(
                    color = currentGuitar.lineColor,
                    start = Offset(
                        x = 0f + currentGuitar.stringSpacing.dp.toPx() * stringIndex,
                        y = 0f
                    ),
                    end = Offset(
                        x = 0f + currentGuitar.stringSpacing.dp.toPx() * stringIndex,
                        // y starts at 2f because otherwise it doesn't quite intersect with frets
                        y = 2f + currentGuitar.fretboardLength.dp.toPx()
                    ),
                    strokeWidth = currentGuitar.strings[stringIndex].thickness.dp.toPx()
                )
            }
            // draw the guitar frets (and guitar nut at the top of the guitar)
            repeat(currentGuitar.numberOfFrets + 1) { fretIndex ->
                drawLine(
                    color = currentGuitar.lineColor,
                    start = Offset(
                        x = 0f,
                        // y starts at 1f because otherwise it doesn't quite intersect with strings
                        y = 1f + currentGuitar.fretSpacing.dp.toPx() * fretIndex
                    ),
                    end = Offset(
                        x = currentGuitar.stringSpacing.dp.toPx() * (currentGuitar.numberOfStrings - 1),
                        // y starts at 1f because otherwise it doesn't quite intersect with strings
                        y = 1f + currentGuitar.fretSpacing.dp.toPx() * fretIndex
                    ),
                    strokeWidth = currentGuitar.fretThickness.dp.toPx()
                )
            }

            // create fret markers
            repeat(currentGuitar.numberOfFrets) { fretIndex ->
                // make fret markers centered if the number of strings is even and less than 8 strings
                if (currentGuitar.numberOfStrings % 2 == 0 && currentGuitar.numberOfStrings < 8) {
                    // draw the fret markers that have a single circle
                    if (
                        fretIndex + 1 in currentGuitar.fretMarkers &&
                        (fretIndex + 1) % 12 != 0
                    ) {
                        drawCircle(
                            currentGuitar.lineColor,
                            radius = currentGuitar.fretMarkerSize.dp.toPx(),
                            center = Offset(
                                x = (currentGuitar.stringSpacing.dp.toPx() * (currentGuitar.numberOfStrings - 1)) / 2,
                                y = currentGuitar.fretSpacing.dp.toPx() * (fretIndex + 0.5).toFloat()
                            )
                        )
                    }
                    // draw the fret markers that have two circles
                    else if (fretIndex + 1 in currentGuitar.fretMarkers && (fretIndex + 1) % 12 == 0) {
                        drawCircle(
                            currentGuitar.lineColor,
                            radius = currentGuitar.fretMarkerSize.dp.toPx(),
                            center = Offset(
                                x = currentGuitar.stringSpacing.dp.toPx() * 1.5.toFloat(),
                                y = currentGuitar.fretSpacing.dp.toPx() * (fretIndex + 0.5).toFloat()
                            )
                        )
                        drawCircle(
                            currentGuitar.lineColor,
                            radius = currentGuitar.fretMarkerSize.dp.toPx(),
                            center = Offset(
                                x = (currentGuitar.stringSpacing.dp.toPx() * (currentGuitar.numberOfStrings - 2))
                                        - 0.5.toFloat() * currentGuitar.stringSpacing.dp.toPx(),
                                y = currentGuitar.fretSpacing.dp.toPx() * (fretIndex + 0.5).toFloat()
                            )
                        )
                    }
                }
                // make markers offset if the number of strings is odd, so that it doesn't overlap a string, or greater than 6
                else if (currentGuitar.numberOfStrings % 2 != 0 || currentGuitar.numberOfStrings > 6) {
                    // draw the fret markers that have a single circle
                    if (
                        fretIndex + 1 in currentGuitar.fretMarkers &&
                        (fretIndex + 1) % 12 != 0
                    ) {
                        drawCircle(
                            currentGuitar.lineColor,
                            radius = currentGuitar.fretMarkerSize.dp.toPx(),
                            center = Offset(
                                x = currentGuitar.stringSpacing.dp.toPx() / 2,
                                y = currentGuitar.fretSpacing.dp.toPx() * (fretIndex + 0.5).toFloat()
                            )
                        )
                    }
                    // draw the fret markers that have two circles
                    else if (fretIndex + 1 in currentGuitar.fretMarkers && (fretIndex + 1) % 12 == 0) {
                        drawCircle(
                            currentGuitar.lineColor,
                            radius = currentGuitar.fretMarkerSize.dp.toPx(),
                            center = Offset(
                                x = currentGuitar.stringSpacing.dp.toPx() / 2,
                                y = currentGuitar.fretSpacing.dp.toPx() * (fretIndex + 0.5).toFloat()
                            )
                        )
                        drawCircle(
                            currentGuitar.lineColor,
                            radius = currentGuitar.fretMarkerSize.dp.toPx(),
                            center = Offset(
                                x = currentGuitar.stringSpacing.dp.toPx() * 1.5.toFloat(),
                                y = currentGuitar.fretSpacing.dp.toPx() * (fretIndex + 0.5).toFloat()
                            )
                        )
                    }
                }
            }

            when (navigationState) {
                "Chord Identification" -> {
                    repeat(currentGuitar.numberOfStrings) { stringIndex ->

                        val intervalColor = IntervalColor(
                            (currentGuitar.strings[stringIndex].tuning.midiValue + currentGuitar.fretMemory[stringIndex].fretSelected
                                    + 12 - (currentGuitar.chordInterpretationMemory.root.midiValue % 12)) % 12
                        )

                        // create indications of fretted notes
                        if (currentGuitar.fretMemory[stringIndex].visible) {
                            drawCircle(
                                color = intervalColor.backgroundColor,
                                radius = currentGuitar.frettedNoteSize.dp.toPx(),
                                center = Offset(
                                    x = currentGuitar.fretMemory[stringIndex].x.dp.toPx(),
                                    y = currentGuitar.fretMemory[stringIndex].y.dp.toPx()
                                ),
                            )

                            var textToDraw = ""

                            val chordMemoryIndex =
                                currentGuitar.chordInterpretationMemory.pitchClassIntValues.indexOfFirst {
                                    it == (currentGuitar.strings[stringIndex].tuning.midiValue + currentGuitar.fretMemory[stringIndex].fretSelected) % 12
                                } // -1 if not found
                            if (chordMemoryIndex >= 0) {
                                textToDraw = currentGuitar.chordInterpretationMemory.noteNames[chordMemoryIndex]
                            }

                            val style = TextStyle(
                                fontSize = 14.sp,
                                color = intervalColor.textColor,
                            )
                            val textLayoutResult = textMeasurer.measure(textToDraw, style)

                            drawText(
                                textMeasurer = textMeasurer,
                                text = textToDraw,
                                style = style,
                                topLeft = Offset(
                                    x = currentGuitar.fretMemory[stringIndex].x.dp.toPx() - textLayoutResult.size.width / 2,
                                    y = currentGuitar.fretMemory[stringIndex].y.dp.toPx() - textLayoutResult.size.height / 2
                                )
                            )
                        }

                        // create X shapes for strings that aren't being played
                        else {
                            val path = Path()
                            path.moveTo(
                                currentGuitar.fretMemory[stringIndex].x.dp.toPx() - currentGuitar.unfrettedSize.dp.toPx(),
                                currentGuitar.fretMemory[stringIndex].y.dp.toPx() - currentGuitar.unfrettedSize.dp.toPx()
                            )
                            path.lineTo(
                                currentGuitar.fretMemory[stringIndex].x.dp.toPx() + currentGuitar.unfrettedSize.dp.toPx(),
                                currentGuitar.fretMemory[stringIndex].y.dp.toPx() + currentGuitar.unfrettedSize.dp.toPx()
                            )
                            path.moveTo(
                                currentGuitar.fretMemory[stringIndex].x.dp.toPx() + currentGuitar.unfrettedSize.dp.toPx(),
                                currentGuitar.fretMemory[stringIndex].y.dp.toPx() - currentGuitar.unfrettedSize.dp.toPx()
                            )
                            path.lineTo(
                                currentGuitar.fretMemory[stringIndex].x.dp.toPx() - currentGuitar.unfrettedSize.dp.toPx(),
                                currentGuitar.fretMemory[stringIndex].y.dp.toPx() + currentGuitar.unfrettedSize.dp.toPx()
                            )
                            path.close()
                            drawPath(path, currentGuitar.lineColor, style = Stroke(width = currentGuitar.lineThickness.dp.toPx()))
                        }
                    }
                }
                "Interval Display" -> {
                    for ((stringIndex, stringLocation) in currentGuitar.stringLocations.withIndex()) {
                        for ((fretIndex, fretLocation) in currentGuitar.fretLocations.withIndex()) {
                            if ((currentGuitar.strings[stringIndex].tuning.midiValue + fretIndex) % 12 in currentGuitar.intervalDisplayMemory.pitchClassIntValues) {

                                val intervalColor = IntervalColor(
                                    (currentGuitar.strings[stringIndex].tuning.midiValue + fretIndex
                                            + 12 - (currentGuitar.intervalDisplayMemory.root.midiValue % 12)) % 12
                                )

                                drawCircle(
                                    color = intervalColor.backgroundColor,
                                    radius = currentGuitar.frettedNoteSize.dp.toPx(),
                                    center = Offset(
                                        x = stringLocation.dp.toPx(),
                                        y = fretLocation.dp.toPx()
                                    )
                                )

                                var textToDraw = ""
                                val chordMemoryIndex = currentGuitar.intervalDisplayMemory.pitchClassIntValues.indexOfFirst { it == (currentGuitar.strings[stringIndex].tuning.midiValue + fretIndex) % 12 } // -1 if not found

                                if (chordMemoryIndex >= 0) {
                                    textToDraw = currentGuitar.intervalDisplayMemory.noteNames[chordMemoryIndex]
                                }

                                val style = TextStyle(
                                    fontSize = 14.sp,
                                    color = intervalColor.textColor,
                                )
                                val textLayoutResult = textMeasurer.measure(textToDraw, style)

                                drawText(
                                    textMeasurer = textMeasurer,
                                    text = textToDraw,
                                    style = style,
                                    topLeft = Offset(
                                        x = stringLocation.dp.toPx() - textLayoutResult.size.width / 2,
                                        y = fretLocation.dp.toPx() - textLayoutResult.size.height / 2
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}