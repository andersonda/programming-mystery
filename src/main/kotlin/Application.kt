import javafx.beans.value.ChangeListener
import javafx.scene.control.SplitPane
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import tornadofx.*
import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter
import kotlin.system.exitProcess

class Application: App(ApplicationView::class)

class ApplicationView: View("Programming Mystery") {
    private val questionView: QuestionView by inject()
    val ioPanelView: IOPanelView by inject()
    val sp = SplitPane()

    override val root = borderpane {
        top =  menubar {
            menu("File") {
                menu("Load"){
                    item("Code").setOnAction {
                        loadQuestion()
                    }
                    item("Teams").setOnAction {
                        loadTeams()
                    }
                }
                item("Quit").setOnAction {
                    exitProcess(0)
                }
            }
            menu("View") {
                menu("Answers") {
                    item("Current") {
                        accelerator = KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_ANY)
                        setOnAction {
                            questionView.viewAnswer()
                        }
                    }
                    item("All").setOnAction {
                        questionView.viewAllAnswers()
                    }
                }
                menu("Zoom") {
                    item("In") {
                        accelerator = KeyCodeCombination(KeyCode.EQUALS, KeyCombination.CONTROL_ANY)
                        setOnAction {
                            questionView.resize(1)
                        }
                    }
                    item("Out"){
                        accelerator = KeyCodeCombination(KeyCode.MINUS, KeyCombination.CONTROL_ANY)
                        setOnAction {
                            questionView.resize(-1)
                        }
                    }
                    item("Reset") {
                        setOnAction {
                            questionView.resize(0)
                        }
                    }
                }
            }
        }
        center = sp
    }

    init {
        loadQuestion()
        sp += questionView.root
        sp += ioPanelView.root
        currentStage?.isMaximized = true

        val changeListener = ChangeListener<Number> { _, _, _ -> sp.setDividerPositions(0.80) }
        sp.widthProperty().addListener(changeListener)
        sp.heightProperty().addListener(changeListener)
    }

    fun loadQuestion(){
        questionView.chooseQuestion()
        questionView.loadAnswers()
        ioPanelView.teamView.loadResponses()
        ioPanelView.navigationView.enableCheck()
    }

    fun loadTeams(){
        val teamPath = File("team-path.txt")
        if(!teamPath.exists()){
            teamPath.writeText("")
        }
        val chooser = JFileChooser()
        chooser.dialogTitle = "Open Team Names"
        chooser.fileFilter = FileNameExtensionFilter("TXT files", "txt")
        chooser.currentDirectory = File(teamPath.readText())
        if(chooser.showOpenDialog(null) != JFileChooser.CANCEL_OPTION) {
            val teams = chooser.selectedFile.readText().lines()
            ioPanelView.teamView.populateTeams(teams)
            ioPanelView.scoresView.populateScores()
            ioPanelView.questionView.resetResponses()
            ioPanelView.resetOutputLine()
            ioPanelView.teamView.loadResponses()
            ioPanelView.navigationView.enableCheck()
            teamPath.writeText(chooser.selectedFile.parent)
        }
    }
}