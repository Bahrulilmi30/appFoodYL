package com.catnip.appfoodyl.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.catnip.appfoodyl.presentation.home.adapter.subadapter.AdapterLayoutMode
import com.catnip.appfoodyl.presentation.home.adapter.subadapter.ItemListAdapter
import com.catnip.appfoodyl.data.model.Menu
import com.catnip.appfoodyl.data.network.api.datasource.ProductApiDataSource
import com.catnip.appfoodyl.data.network.api.datasource.ProductDataSource
import com.catnip.appfoodyl.data.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.catnip.appfoodyl.data.network.service.ProductService
import com.catnip.appfoodyl.data.repository.ProductRepository
import com.catnip.appfoodyl.data.repository.ProductRepositoryImpl
import com.catnip.appfoodyl.data.repository.UserRepositoryImpl
import com.catnip.appfoodyl.databinding.ActivityMainBinding
import com.catnip.appfoodyl.databinding.FragmentHomeBinding
import com.catnip.appfoodyl.datastore.UserPreferenceDataSourceImpl
import com.catnip.appfoodyl.datastore.appDataStore
import com.catnip.appfoodyl.presentation.detail.DetailActivity
import com.catnip.appfoodyl.utils.GenericViewModelFactory
import com.catnip.appfoodyl.utils.PreferenceDataStoreHelperImpl
import com.catnip.appfoodyl.utils.proceedWhen
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels {
        val service = ProductService.invoke()
        val dataSource: ProductDataSource = ProductApiDataSource(service)
        val repository: ProductRepository = ProductRepositoryImpl(dataSource)

        val dataStore = this.requireContext().appDataStore
        val dataStoreHelper = PreferenceDataStoreHelperImpl(dataStore)
        val userPreferenceDataSource = UserPreferenceDataSourceImpl(dataStoreHelper)

        val firebaseDataSource = FirebaseAuthDataSourceImpl(FirebaseAuth.getInstance())
        val userRepository = UserRepositoryImpl(firebaseDataSource)
        GenericViewModelFactory.create(
            HomeViewModel(
                repository,
                userPreferenceDataSource,
                userRepository
            )
        )
    }

    private val itemListAdapter: ItemListAdapter by lazy {
        ItemListAdapter(
            adapterLayoutMode = AdapterLayoutMode.GRID,
            onItemClick = { navigateToActivityDetail(it) }
        )
    }

    private fun navigateToActivityDetail(it: Menu) {
        DetailActivity.startActivity(requireContext(), it)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        invokeData()
        observeData()
        setupSwitch()
        setProfileData()

    }

    private fun setProfileData() {
        val name = viewModel.getUserData()?.fullName ?: "Binarian"
        binding.tv.text = getString(R.string.text_greeting_user_name, name)
    }
    private fun invokeData() {
        viewModel.getCategories()
        viewModel.getMenus()
    }
    private fun observeData() {
        observeCategory()
        observeMenu()
    }

    private fun observeCategory() {
        viewModel.categories.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = { result ->
                    binding.rvCategory.isVisible = true
                    binding.categoryLayoutState.tvError.isVisible = false
                    binding.categoryLayoutState.pbLoading.isVisible = false

                    result.payload?.let { category ->
                        categoryListAdapter.setData(category)
                    }
                },
                doOnLoading = {
                    binding.categoryLayoutState.root.isVisible = true
                    binding.categoryLayoutState.pbLoading.isVisible = true
                    binding.rvCategory.isVisible = false
                },
                doOnError = {
                    binding.categoryLayoutState.root.isVisible = true
                    binding.categoryLayoutState.pbLoading.isVisible = false
                    binding.categoryLayoutState.tvError.isVisible = true
                    binding.categoryLayoutState.tvError.text = it.exception?.message.orEmpty()
                    binding.rvCategory.isVisible = false
                }
            )
        }
    }

    private fun observeMenu() {
        viewModel.menus.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = { result ->
                    binding.rvFoods.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    result.payload?.let { menu ->
                        foodListAdapter.setData(menu)
                    }
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.rvFoods.isVisible = false

                },
                doOnError = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = it.exception?.message.orEmpty()
                    binding.rvFoods.isVisible = false
                }
            )
        }
    }
    private fun setupSwitch() {
        viewModel.getUserListViewLiveData().observe(viewLifecycleOwner) { isUsingList ->
            val icon = if (isUsingList) R.drawable.ic_linear else R.drawable.ic_grid
            (binding.rvFoods.layoutManager as GridLayoutManager).spanCount =
                if (isUsingList) 1 else 2
            foodListAdapter.adapterLayoutMode =
                if (isUsingList) AdapterLayoutMode.LINEAR else AdapterLayoutMode.GRID
            binding.ivSwitchLayout.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    icon
                )
            )
            foodListAdapter.refreshList()
            setSwitchClickListener(isUsingList)
        }
    }
    private fun setSwitchClickListener(usingList: Boolean) {
        binding.ivSwitchLayout.setOnClickListener {
            if (usingList) {
                viewModel.setUserListViewMode(false)
            } else {
                viewModel.setUserListViewMode(true)
            }
        }
    }
    private fun setupRecyclerView() {
        setupCategoryRecyclerView()
        setupMenuRecyclerView()
    }
    private fun setupMenuRecyclerView() {
        val span = if (foodListAdapter.adapterLayoutMode == AdapterLayoutMode.LINEAR) 1 else 2
        binding.rvFoods.adapter = foodListAdapter
        binding.rvFoods.layoutManager = GridLayoutManager(requireContext(), span)
    }
    private fun setupCategoryRecyclerView() {
        binding.rvCategory.adapter = categoryListAdapter

    }
    private fun getProductData() {
        viewModel.getProducts()
    }
}
