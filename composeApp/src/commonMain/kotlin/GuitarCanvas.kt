import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import classes.Guitar

@Composable
fun GuitarCanvas(
    navigationState: String,
    innerPadding: PaddingValues,
    currentGuitar: Guitar,
    onGuitarChange: (Guitar) -> Unit
) {
    Canvas(modifier = Modifier
        .height((insetVertical * 4 + currentGuitar.fretSpacing * settings.getInt("number of frets", 12)).dp)
        .width((insetHorizontal * 2 + currentGuitar.stringSpacing * ( settings.getInt("number of strings", 6) - 1)).dp)
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

            if (navigationState == "Chord Identification") {
                repeat(currentGuitar.numberOfStrings) { stringIndex ->
                    // create indications of fretted notes
                    if (currentGuitar.fretMemory[stringIndex].visible) {
                        drawCircle(
                            currentGuitar.lineColor,
                            radius = currentGuitar.frettedNoteSize.dp.toPx(),
                            style = Stroke(width = currentGuitar.lineThickness.dp.toPx()),
                            center = Offset(
                                x = currentGuitar.fretMemory[stringIndex].x.dp.toPx(),
                                y = currentGuitar.fretMemory[stringIndex].y.dp.toPx()
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
        }
    }
}