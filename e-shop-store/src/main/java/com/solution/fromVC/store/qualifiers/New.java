package com.solution.fromVC.store.qualifiers;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * Created by Влад on 21.11.2016.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD,FIELD,PARAMETER,TYPE})
public @interface New {
}
