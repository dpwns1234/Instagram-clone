package com.instagram.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.navigation.NavDirections
import com.instagram.databinding.FragmentSearchBinding
import com.instagram.model.Profile

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = SearchViewModel()
        val searchAdapter = SearchAdapter()
        binding.rvSearchHistory.adapter = searchAdapter
        binding.lifecycleOwner = viewLifecycleOwner

        // profiles 데이터를 모두 가져왔을 때 동작하도록
        viewModel.profiles.observe(viewLifecycleOwner) { profiles ->
            with(binding.etSearch) {
                // 검색창에 새로 입력할 때마다 검색
                this.doAfterTextChanged {
                    val searchText = this.text.toString()
                    val searchUserProfiles = mutableListOf<Profile>()

                    // 검색한 내용과 닉네임이 일치하면 모두 저장 후
                    for (profile in profiles) {
                        if (profile.nickname.lowercase()
                                .contains(searchText.lowercase()) && searchText.isNotBlank()
                        )
                            searchUserProfiles.add(profile)
                    }
                    // adapter에 binding하여 검색 결과를 보여준다.
                    searchAdapter.submitList(searchUserProfiles)
                }

                this.doOnTextChanged { text, start, count, after -> }

                this.doBeforeTextChanged { charSequence: CharSequence?, i: Int, i1: Int, i2: Int -> }

                // 사용자가 엔터(검색)키를 눌렀을 때
                this.setOnEditorActionListener { text, p1, p2 ->
                    Log.d("hello", "text: ${text.text.toString()}")
                    true
                }
            }
        }


    }
}