package com.lupa.suitgame2.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.lupa.suitgame2.R
import com.lupa.suitgame2.databinding.ActivityGameBinding
import com.lupa.suitgame2.enum.PlayerChar
import com.lupa.suitgame2.enum.PlayerSide
import com.lupa.suitgame2.ui.dialog.DialogUtils
import com.lupa.suitgame2.usecase.GetWinnerUseCase
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private val binding: ActivityGameBinding by lazy {
        ActivityGameBinding.inflate(layoutInflater)
    }

    private var playerOne: Int = PlayerChar.ROCK.ordinal
    private var playerTwo: Int = PlayerChar.ROCK.ordinal

    private var multiplayerMode: Int = PLAYER_VS_ROBOT

    private lateinit var onWinnerGame: WinnerPlayer

    val name by lazy {
        intent.getStringExtra(EXTRAS_PLAYER_NAME)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        multiplayerMode = intent.extras?.getInt(EXTRAS_MULTIPLAYER_MODE) ?: 0
        onWinnerGame = GetWinnerUseCase()

        setOnCLickListeners()
        supportActionBar?.hide()
        imgLoad()

        with(binding) {
            Snackbar.make(
                root,
                "${name?.replaceFirstChar { it.uppercase() }} Turn",
                Snackbar.LENGTH_SHORT
            ).show()
            ivRefresh.setOnClickListener {
                resetGame()
                Snackbar.make(
                    root,
                    "${name?.replaceFirstChar { it.uppercase() }} Turn",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            tvUser.text = "${name?.replaceFirstChar { it.uppercase() }}"
        }
    }

    private fun imgLoad() {
        Glide.with(this@GameActivity)
            .load("https://lilgoods.com/ic_logo_title.png")
            .into(binding.ivLogoTitle)
    }

    private fun showDialogResult() {
        when (onWinnerGame.onWinnerPlayer(this.playerOne, this.playerTwo)) {
            GetWinnerUseCase.DRAW -> {
                DialogUtils.showInputNameDialog(context = this, "DRAW!!!") { dialog, value ->
                    dialog?.dismiss()
                    if (value == "back") {
                        finish()
                    } else {
                        resetGame()
                        Snackbar.make(
                            binding.root,
                            "${name?.replaceFirstChar { it.uppercase() }} Turn",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            GetWinnerUseCase.PLAYER_ONE_WINNER -> {
                DialogUtils.showInputNameDialog(
                    context = this,
                    "${name?.replaceFirstChar { it.uppercase() }} Won!!!"
                ) { dialog, value ->
                    dialog?.dismiss()
                    if (value == "back") {
                        finish()
                    } else {
                        resetGame()
                        Snackbar.make(
                            binding.root,
                            "${name?.replaceFirstChar { it.uppercase() }} Turn",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            GetWinnerUseCase.PLAYER_TWO_WINNER -> {
                DialogUtils.showInputNameDialog(
                    context = this,
                    "Player 2 \n Won!!!"
                ) { dialog, value ->
                    dialog?.dismiss()
                    if (value == "back") {
                        finish()
                    } else {
                        resetGame()
                        Snackbar.make(
                            binding.root,
                            "${name?.replaceFirstChar { it.uppercase() }} Turn",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun resetGame() {
        if (multiplayerMode == PLAYER_VS_CAT) {
            binding.btnSelectUser.visibility = View.INVISIBLE
            binding.btnSelectPlayer2.visibility = View.INVISIBLE
        }
        binding.apply {
            ivLeftRock.isEnabled = true
            ivLeftPaper.isEnabled = true
            ivLeftScissor.isEnabled = true

            ivLeftRock.setImageResource(R.drawable.ic_left_rock)
            ivLeftPaper.setImageResource(R.drawable.ic_left_paper)
            ivLeftScissor.setImageResource(R.drawable.ic_left_scissor)
            ivRightRock.setImageResource(R.drawable.ic_right_rock)
            ivRightPaper.setImageResource(R.drawable.ic_right_paper)
            ivRightScissor.setImageResource(R.drawable.ic_right_scissor)
        }
    }

    private fun setOnCLickListeners() {
        if (multiplayerMode == PLAYER_VS_CAT) {
            binding.apply {
                ivRightRock.isEnabled = false
                ivRightPaper.isEnabled = false
                ivRightScissor.isEnabled = false

                btnSelectPlayer2.setOnClickListener {
                    ivRightRock.isEnabled = false
                    ivRightPaper.isEnabled = false
                    ivRightScissor.isEnabled = false
                    btnSelectPlayer2.visibility = View.INVISIBLE
                    showDialogResult()
                }
            }
            binding.ivLeftRock.setOnClickListener {
                playerOne = PlayerChar.ROCK.ordinal
                setPlayerMovement(PlayerSide.PLAYER_ONE, playerOne)
                binding.apply {
                    btnSelectUser.visibility = View.VISIBLE
                    btnSelectUser.setOnClickListener {
                        ivRightRock.isEnabled = true
                        ivRightPaper.isEnabled = true
                        ivRightScissor.isEnabled = true
                        ivLeftRock.isEnabled = false
                        ivLeftPaper.isEnabled = false
                        ivLeftScissor.isEnabled = false
                        btnSelectUser.visibility = View.INVISIBLE
                        btnSelectPlayer2.visibility = View.VISIBLE

                        Snackbar.make(binding.root, "Player 2 Turn", Snackbar.LENGTH_SHORT).show()
                        ivLeftRock.setImageResource(
                            R.drawable.ic_left_rock
                        )
                    }
                }
            }
            binding.ivLeftPaper.setOnClickListener {
                playerOne = PlayerChar.PAPER.ordinal
                setPlayerMovement(PlayerSide.PLAYER_ONE, playerOne)
                binding.apply {
                    btnSelectUser.visibility = View.VISIBLE
                    btnSelectUser.setOnClickListener {
                        ivRightRock.isEnabled = true
                        ivRightPaper.isEnabled = true
                        ivRightScissor.isEnabled = true
                        ivLeftRock.isEnabled = false
                        ivLeftPaper.isEnabled = false
                        ivLeftScissor.isEnabled = false
                        btnSelectUser.visibility = View.INVISIBLE
                        btnSelectPlayer2.visibility = View.VISIBLE

                        Snackbar.make(binding.root, "Player 2 Turn", Snackbar.LENGTH_SHORT).show()
                        ivLeftPaper.setImageResource(
                            R.drawable.ic_left_paper
                        )
                    }
                }
            }
            binding.ivLeftScissor.setOnClickListener {
                playerOne = PlayerChar.SCISSOR.ordinal
                setPlayerMovement(PlayerSide.PLAYER_ONE, playerOne)
                binding.apply {
                    btnSelectUser.visibility = View.VISIBLE
                    btnSelectUser.setOnClickListener {
                        ivRightRock.isEnabled = true
                        ivRightPaper.isEnabled = true
                        ivRightScissor.isEnabled = true
                        ivLeftRock.isEnabled = false
                        ivLeftPaper.isEnabled = false
                        ivLeftScissor.isEnabled = false
                        btnSelectUser.visibility = View.INVISIBLE
                        btnSelectPlayer2.visibility = View.VISIBLE

                        Snackbar.make(binding.root, "Player 2 Turn", Snackbar.LENGTH_SHORT).show()
                        ivLeftScissor.setImageResource(
                            R.drawable.ic_left_scissor
                        )
                    }
                }
            }
            binding.apply {
                ivRightRock.setOnClickListener {
                    playerTwo = PlayerChar.ROCK.ordinal
                    setPlayerMovement(PlayerSide.PLAYER_TWO, playerTwo)
                }
                ivRightPaper.setOnClickListener {
                    playerTwo = PlayerChar.PAPER.ordinal
                    setPlayerMovement(PlayerSide.PLAYER_TWO, playerTwo)
                }
                ivRightScissor.setOnClickListener {
                    playerTwo = PlayerChar.SCISSOR.ordinal
                    setPlayerMovement(PlayerSide.PLAYER_TWO, playerTwo)
                }
            }
        } else {
            binding.apply {
                ivLeftRock.setOnClickListener {
                    Snackbar.make(
                        binding.root,
                        "${name?.replaceFirstChar { it.uppercase() }} Choose Rock",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    playerOne = PlayerChar.ROCK.ordinal
                    playerTwo = PlayerChar.values()[Random.nextInt(0, 3)].ordinal
                    setPlayerMovement(PlayerSide.PLAYER_ONE, playerOne)
                    setPlayerMovement(PlayerSide.PLAYER_TWO, playerTwo)
                    showDialogResult()
                }
                ivLeftPaper.setOnClickListener {
                    Snackbar.make(
                        binding.root,
                        "${name?.replaceFirstChar { it.uppercase() }} Choose Paper",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    playerOne = PlayerChar.PAPER.ordinal
                    playerTwo = PlayerChar.values()[Random.nextInt(0, 3)].ordinal
                    setPlayerMovement(PlayerSide.PLAYER_ONE, playerOne)
                    setPlayerMovement(PlayerSide.PLAYER_TWO, playerTwo)
                    showDialogResult()
                }
                ivLeftScissor.setOnClickListener {
                    Snackbar.make(
                        binding.root,
                        "${name?.replaceFirstChar { it.uppercase() }} Choose Scissor",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    playerOne = PlayerChar.SCISSOR.ordinal
                    playerTwo = PlayerChar.values()[Random.nextInt(0, 3)].ordinal
                    setPlayerMovement(PlayerSide.PLAYER_ONE, playerOne)
                    setPlayerMovement(PlayerSide.PLAYER_TWO, playerTwo)
                    showDialogResult()
                }
                ivRefresh.visibility = View.GONE
            }
        }
    }

    private fun setPlayerMovement(playerSide: PlayerSide, playerChar: Int) {
        if (playerSide == PlayerSide.PLAYER_ONE && playerChar == PlayerChar.ROCK.ordinal) {
            binding.apply {
                ivLeftRock.setImageResource(
                    R.drawable.ic_left_rock_highlight
                )
                ivLeftPaper.setImageResource(
                    R.drawable.ic_left_paper
                )
                ivLeftScissor.setImageResource(
                    R.drawable.ic_left_scissor
                )
            }
        } else if (playerSide == PlayerSide.PLAYER_ONE && playerChar == PlayerChar.PAPER.ordinal) {
            binding.apply {
                ivLeftRock.setImageResource(
                    R.drawable.ic_left_rock
                )
                ivLeftPaper.setImageResource(
                    R.drawable.ic_left_paper_highlight
                )
                ivLeftScissor.setImageResource(
                    R.drawable.ic_left_scissor
                )
            }
        } else if (playerSide == PlayerSide.PLAYER_ONE && playerChar == PlayerChar.SCISSOR.ordinal) {
            binding.apply {
                ivLeftRock.setImageResource(
                    R.drawable.ic_left_rock
                )
                ivLeftPaper.setImageResource(
                    R.drawable.ic_left_paper
                )
                ivLeftScissor.setImageResource(
                    R.drawable.ic_left_scissor_highlight
                )
            }
        } else if (playerSide == PlayerSide.PLAYER_TWO && playerChar == PlayerChar.ROCK.ordinal) {
            binding.apply {
                ivRightRock.setImageResource(
                    R.drawable.ic_right_rock_highlight
                )
                ivRightPaper.setImageResource(
                    R.drawable.ic_right_paper
                )
                ivRightScissor.setImageResource(
                    R.drawable.ic_right_scissor
                )
            }
        } else if (playerSide == PlayerSide.PLAYER_TWO && playerChar == PlayerChar.PAPER.ordinal) {
            binding.apply {
                ivRightRock.setImageResource(
                    R.drawable.ic_right_rock
                )
                ivRightPaper.setImageResource(
                    R.drawable.ic_right_paper_highlight
                )
                ivRightScissor.setImageResource(
                    R.drawable.ic_right_scissor
                )
            }
        } else if (playerSide == PlayerSide.PLAYER_TWO && playerChar == PlayerChar.SCISSOR.ordinal) {
            binding.apply {
                ivRightRock.setImageResource(
                    R.drawable.ic_right_rock
                )
                ivRightPaper.setImageResource(
                    R.drawable.ic_right_paper
                )
                ivRightScissor.setImageResource(
                    R.drawable.ic_right_scissor_highlight
                )
            }
        }
    }

    companion object {
        const val EXTRAS_MULTIPLAYER_MODE = "EXTRAS_MULTIPLAYER_MODE"
        const val EXTRAS_PLAYER_NAME = "EXTRAS_PLAYER_NAME"

        const val PLAYER_VS_ROBOT = 0
        const val PLAYER_VS_CAT = 1
    }
}

interface WinnerPlayer {
    fun onWinnerPlayer(playerOne: Int, playerTwo: Int): Int
}