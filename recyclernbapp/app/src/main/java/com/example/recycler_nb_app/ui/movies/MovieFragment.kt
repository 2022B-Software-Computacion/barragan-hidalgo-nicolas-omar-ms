package com.example.recycler_nb_app.ui.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recycler_nb_app.R
import com.example.recycler_nb_app.databinding.ActivityMainBinding
import com.example.recycler_nb_app.databinding.FragmentHomeBinding
import com.example.recycler_nb_app.databinding.FragmentMovieBinding
import com.example.recycler_nb_app.ui.home.HomeViewModel
import com.example.recycler_nb_app.ui.recycler.MoviesCatalogListAdapter
import kotlin.text.Typography.dagger

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MovieFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class MovieFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private val moviesCatalogListAdapter = MoviesCatalogListAdapter()
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    //private val moviesCatalogListAdapter: MoviesCatalogListAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_movie, container, false)
        recyclerView = view.findViewById(R.id.moviesCatalog)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = moviesCatalogListAdapter
        subscribeObservers()
        viewModel.getMoviesCatalog()
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.catalogMovies)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        viewModel.getMoviesCatalog()
        recyclerView.adapter = MoviesCatalogListAdapter()
        */

        //subscribeObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        
    }
    private fun subscribeObservers() {
        viewModel.catalogList.observe(viewLifecycleOwner) {
            moviesCatalogListAdapter.submitList(it)
        }
    }


    /*
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MovieFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MovieFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }*/
}