package com.madrat.kursovaya.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.madrat.kursovaya.R
import com.madrat.kursovaya.adapters.GetSimilarRecipesAdapter
import com.madrat.kursovaya.adapters.get_recipe_nutrition_widget_by_id.NutritionalValueAdapter
import com.madrat.kursovaya.databinding.FragmentGetSimilarRecipesBinding
import com.madrat.kursovaya.interfaces.GetSimilarRecipesMVP
import com.madrat.kursovaya.model.get_recipe_nutrition_widget_by_id.Nutrition
import com.madrat.kursovaya.model.get_similair_recipes.SimilarRecipe
import com.madrat.kursovaya.presenters.GetRecipeNutritionWidgetByIdPresenter
import com.madrat.kursovaya.presenters.GetSimilarRecipesPresenter
import com.madrat.kursovaya.repository.GetRecipeNutritionWidgetByIdRepository
import com.madrat.kursovaya.repository.GetSimilarRecipesRepository
import com.madrat.kursovaya.util.linearManager

class GetSimilarRecipesView
    : Fragment(), GetSimilarRecipesMVP.View {
    private val args: GetSimilarRecipesViewArgs by navArgs()

    // ViewBinding variables
    private var mBinding: FragmentGetSimilarRecipesBinding? = null
    private val binding get() = mBinding!!

    private var adapter: GetSimilarRecipesAdapter? = null
    private var presenter: GetSimilarRecipesPresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.title = "SimilarRecipes"

        // Initialize ViewBinding
        mBinding = FragmentGetSimilarRecipesBinding.inflate(inflater,
            container, false)
        val view = binding.root

        initializeAdapterAndRecyclerView()

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializePresenter()

        presenter?.getSimilarRecipesData(
            view.context, args.recipeId,
            view.context.getString(R.string.API_KEY), 3
        )
    }

    // Initialization
    override fun initializeAdapterAndRecyclerView() {
        adapter = GetSimilarRecipesAdapter()

        binding.recyclerView.linearManager()
        binding.recyclerView.adapter = adapter
    }
    override fun initializePresenter() {
        presenter = GetSimilarRecipesPresenter(
            this, GetSimilarRecipesRepository()
        )
    }

    // Update UI after receiving data
    override fun showListOfSimilarRecipes(listOfSimilarRecipes: ArrayList<SimilarRecipe>) {
        adapter?.updateListOfSimilarRecipes(listOfSimilarRecipes)
        binding.recyclerView.adapter = adapter
    }
    override fun showRecyclerView() {
        binding.recyclerView.isVisible = true
    }
}