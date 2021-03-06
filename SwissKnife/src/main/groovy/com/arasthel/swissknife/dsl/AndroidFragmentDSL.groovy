package com.arasthel.swissknife.dsl

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.arasthel.swissknife.dsl.components.FragmentTransactionBuilder
import groovy.transform.CompileStatic

/**
 * Created by Arasthel on 12/03/15.
 */
@CompileStatic
public class AndroidFragmentDSL {

    /**
     * Replaces fragment with another one
     * @param self
     * @param transactionSpec
     */
    public static void replaceFragment(FragmentActivity self,
                                @DelegatesTo(value=FragmentTransactionBuilder, strategy = Closure.DELEGATE_FIRST) Closure transactionSpec) {
        def transaction = new FragmentTransactionBuilder()
        def clone = (Closure) transactionSpec.clone()
        clone.resolveStrategy = Closure.DELEGATE_FIRST
        clone.delegate = transaction
        clone()
        transaction.activity = self
        transaction.buildReplace().commit()
    }

    /**
     * Adds fragment
     * @param self
     * @param transactionSpec
     */
    public static void addFragment(FragmentActivity self,
                            @DelegatesTo(value=FragmentTransactionBuilder, strategy = Closure.DELEGATE_FIRST) Closure transactionSpec) {
        def transaction = new FragmentTransactionBuilder()
        def clone = (Closure) transactionSpec.clone()
        clone.resolveStrategy = Closure.DELEGATE_FIRST
        clone.delegate = transaction
        clone()
        transaction.activity = self
        transaction.buildAdd().commit()
    }

    /**
     * Creates fragment instance with bundle from args
     *
     * @param fragmentClass
     * @param context
     * @param args
     * @return
     */
    public static Fragment withArgs(Class<? extends Fragment> fragmentClass, Context context, Map<String, ?> args) {
        Bundle bundle = AndroidStaticBundleDSL.fromMap(null, args)
        return Fragment.instantiate(context, fragmentClass.getName(), bundle)
    }

    /**
     * Creates fragment instance with bundle
     *
     * @param fragmentClass
     * @param context
     * @param args
     * @return
     */
    public static Fragment withArgs(Class<? extends Fragment> fragmentClass, Context context, Bundle args) {
        return Fragment.instantiate(context, fragmentClass.getName(), args)
    }

}
