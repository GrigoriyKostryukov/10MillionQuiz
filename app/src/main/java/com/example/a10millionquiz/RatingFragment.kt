package com.example.a10millionquiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.a10millionquiz.database.QuestionDatabase
import com.example.a10millionquiz.databinding.FragmentRatingBinding

class RatingFragment : Fragment() {

    private lateinit var viewModel: RatingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentRatingBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_rating, container, false
        )

        val application = requireNotNull(this.activity).application
        val dao = QuestionDatabase.getInstance(application).getQuestionDatabaseDao()
        val viewModelFactory = RatingViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(RatingViewModel::class.java)

        val adapter = UserResultAdapter()
        binding.userRatingList.adapter = adapter

        viewModel.rating.observe(viewLifecycleOwner, Observer { results ->
            if (results != null)
                adapter.data = results
        })

        return binding.root
    }

}