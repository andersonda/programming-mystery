import javafx.beans.value.ChangeListener
import javafx.scene.control.SplitPane
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import tornadofx.*
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
                        SelectTeamsView().apply {
                            openWindow()
                        }
                    }
                }
                item("Quit").setOnAction {
                    exitProcess(0)
                }
            }
            menu("Edit"){
                item("New Teams").setOnAction {
                    CreateTeamsView().apply {
                        openWindow()
                    }
                }
            }
            menu("View") {
                menu("Answers") {
                    item("Current") {
                        setOnAction {
                            questionView.viewAnswer()
                        }
                    }
                    item("All").setOnAction {
                        questionView.viewAllAnswers()
                    }
                    item("None").setOnAction {
                        questionView.removeAllAnswers()
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

        loadTeams()
    }

    fun loadQuestion(){
        questionView.chooseQuestion()
        questionView.loadAnswers()
        ioPanelView.teamView.loadResponses()
        ioPanelView.navigationView.enableCheck()
    }

    fun loadTeams(){
        val teams = DB.getTeams(DB.getPropertyValue(DB.Names.LAST_CLASS))
        ioPanelView.teamView.populateTeams(teams)
        ioPanelView.scoresView.populateScores()
        ioPanelView.questionView.resetResponses()
        ioPanelView.resetOutputLine()
        ioPanelView.teamView.loadResponses()
        ioPanelView.navigationView.enableCheck()
        questionView.removeAllAnswers()
    }
}