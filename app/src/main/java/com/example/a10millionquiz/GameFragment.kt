package com.example.a10millionquiz


import android.os.Bundle
import android.text.format.DateUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.a10millionquiz.database.QuestionDatabase
import com.example.a10millionquiz.databinding.FragmentGameBinding


/**
 * A simple [Fragment] subclass.
 * Use the [GameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameFragment : Fragment() {
    private lateinit var viewModel: GameViewModel
    private lateinit var binding: FragmentGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game,
            container,
            false
        )
        binding.submitButton.setOnClickListener {
            val betsValid = viewModel.validateBets(listOf(
                binding.betA.text.toString(),
                binding.betB.text.toString(),
                binding.betC.text.toString(),
                binding.betD.text.toString()))
            if (betsValid)
                viewModel.onSubmit()
            else
                BetErrorDialogFragment().show(
                    childFragmentManager, BetErrorDialogFragment.TAG)
        }

        val application = requireNotNull(this.activity).application
        val dao = QuestionDatabase.getInstance(application).getQuestionDatabaseDao()
        val viewModelFactory = GameViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(GameViewModel::class.java)

        viewModel.currentQuestion.observe(viewLifecycleOwner, Observer { question ->
            binding.questionText.text = question
        })
        viewModel.score.observe(viewLifecycleOwner, Observer { score ->
            binding.totalSum.text = score.toString()
        })
        viewModel.currentQuestionNumber.observe(viewLifecycleOwner, Observer { number ->
            binding.questionNumber.text = "Вопрос ${number}"
        })
        viewModel.destination.observe(viewLifecycleOwner, Observer { destination ->
            if (destination != null) {
                when (destination) {
                    NavigationDestination.SHOW_VICTORY -> {
                        Navigation.findNavController(view!!)
                            .navigate(R.id.action_gameFragment_to_victoryFragment)
                    }
                    NavigationDestination.SHOW_GAME_OVER -> {
                        Navigation.findNavController(view!!)
                            .navigate(R.id.action_gameFragment_to_gameOverFragment)
                    }
                }
            }
        })
        viewModel.questionAnswers.observe(viewLifecycleOwner) { answers ->
            binding.answerA.text = answers[0].answer
            binding.answerB.text = answers[1].answer
            binding.answerC.text = answers[2].answer
            binding.answerD.text = answers[3].answer
        }
        viewModel.currentTime.observe(viewLifecycleOwner, Observer { time ->
            binding.countdownTimer.text = DateUtils.formatElapsedTime(time)
        })

        return binding.root
    }

}