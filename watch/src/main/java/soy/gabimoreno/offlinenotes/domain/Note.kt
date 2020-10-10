package soy.gabimoreno.offlinenotes.domain

data class Note(
        var text: String = ""
) {
    companion object {
        const val MAX_NOTES = 10
    }
}
