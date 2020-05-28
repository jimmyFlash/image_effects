package com.jimmy.imagefilters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.jimmy.imagefilters.model.Image

class ImageEffectPagerAdapter(private val imageList: List<Image>, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

  override fun getCount(): Int = imageList.size

  override fun getItem(position: Int): Fragment {
    return ImageEffectFragment.newInstance(imageList[position])
  }


}