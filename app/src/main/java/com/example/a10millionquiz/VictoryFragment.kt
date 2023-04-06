package com.example.a10millionquiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.viewpager2.adapter.FragmentViewHolder
import com.example.a10millionquiz.database.QuestionDatabase
import com.example.a10millionquiz.databinding.FragmentGameBinding
import com.example.a10millionquiz.databinding.FragmentVictoryBinding

class VictoryFragment : Fragment() {
    private lateinit var binding: FragmentVictoryBinding
    lateinit var args: VictoryFragmentArgs
    private lateinit var viewModel: VictoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = VictoryFragmentArgs.fromBundle(requireArguments())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val application = requireNotNull(this.activity).application
        val dao = QuestionDatabase.getInstance(application).getQuestionDatabaseDao()
        val viewModelFactory = VictoryViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(VictoryViewModel::class.java)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_victory,
            container,
            false
        )
        binding.cancelRating.setOnClickListener {
            view?.findNavController()?.navigate(
                VictoryFragmentDirections.actionVictoryFragmentToTitleFragment()
            )
        }
        binding.saveRating.setOnClickListener {
            viewModel.onSaveResult(binding.editTextTextPersonName.text.toString(), args.finalScore)
            view?.findNavController()?.navigate(
                VictoryFragmentDirections.actionVictoryFragmentToTitleFragment()
            )
        }

        binding.finalScore.text = args.finalScore.toString()
        return binding.root
    }


}