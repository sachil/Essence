package xyz.sachil.essence.widget

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import java.util.*

@Navigator.Name("fixedFragment")
class FixedFragmentNavigator(
    private val context: Context,
    private val fragmentManager: FragmentManager,
    private val containerId: Int
) : FragmentNavigator(context, fragmentManager, containerId) {
    companion object {
        private const val TAG = "FixedFragmentNavigator"
    }

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {
        if (fragmentManager.isStateSaved) {
            Log.i(TAG, "Ignoring navigate() call: FragmentManager has already saved its state")
            return null
        }
        var className = destination.className
        if (className[0] == '.') {
            className = context.packageName + className
        }
//        val frag = instantiateFragment(context, fragmentManager, className, args)
//        frag.arguments = args
        val ft = fragmentManager.beginTransaction()

        var enterAnim = navOptions?.enterAnim ?: -1
        var exitAnim = navOptions?.exitAnim ?: -1
        var popEnterAnim = navOptions?.popEnterAnim ?: -1
        var popExitAnim = navOptions?.popExitAnim ?: -1
        if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
            enterAnim = if (enterAnim != -1) enterAnim else 0
            exitAnim = if (exitAnim != -1) exitAnim else 0
            popEnterAnim = if (popEnterAnim != -1) popEnterAnim else 0
            popExitAnim = if (popExitAnim != -1) popExitAnim else 0
            ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        }

        val currentDestination = fragmentManager.primaryNavigationFragment
        val nextDestinationTag = destination.id.toString()
        if (currentDestination != null) {
            if (currentDestination.tag == nextDestinationTag) {
                Log.e(TAG, "----------")
                return null
            }
            Log.e(TAG,"hide----")
            ft.hide(currentDestination)
        }
        var nextDestination = fragmentManager.findFragmentByTag(nextDestinationTag)
        if (nextDestination != null) {
            Log.e(TAG,"show------")
            ft.show(nextDestination)
        } else {
            nextDestination = instantiateFragment(context, fragmentManager, className, args)
            nextDestination.arguments = args
            ft.add(containerId, nextDestination, nextDestinationTag)
        }
        ft.setPrimaryNavigationFragment(nextDestination)

        @IdRes val destId = destination.id
        //使用反射得到mBackStack
        val backStack: ArrayDeque<Int>
        val field = FragmentNavigator::class.java.getDeclaredField("mBackStack")
        field.isAccessible = true
        backStack = field.get(this) as ArrayDeque<Int>

        val initialNavigation = backStack.isEmpty()
        // TODO Build first class singleTop behavior for fragments
        // TODO Build first class singleTop behavior for fragments
        val isSingleTopReplacement = (navOptions != null && !initialNavigation
                && navOptions.shouldLaunchSingleTop()
                && backStack.peekLast() == destId)

        val isAdded: Boolean = if (initialNavigation) {
            true
        } else if (isSingleTopReplacement) {
            // Single Top means we only want one instance on the back stack
            if (backStack.size > 1) {
                // If the Fragment to be replaced is on the FragmentManager's
                // back stack, a simple replace() isn't enough so we
                // remove it from the back stack and put our replacement
                // on the back stack in its place
                fragmentManager.popBackStack(
                    generateBackStackName(backStack.size, backStack.peekLast()),
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                ft.addToBackStack(generateBackStackName(backStack.size, destId))
            }
            false
        } else {
            ft.addToBackStack(generateBackStackName(backStack.size + 1, destId))
            true
        }
        if (navigatorExtras is Extras) {
            for ((key, value) in navigatorExtras.sharedElements) {
                ft.addSharedElement(key!!, value!!)
            }
        }
        ft.setReorderingAllowed(true)
        ft.commit()
        // The commit succeeded, update our view of the world
        // The commit succeeded, update our view of the world
        return if (isAdded) {
            backStack.add(destId)
            destination
        } else {
            null
        }
    }

    private fun generateBackStackName(backStackIndex: Int, destId: Int): String {
        return "$backStackIndex-$destId"
    }
}