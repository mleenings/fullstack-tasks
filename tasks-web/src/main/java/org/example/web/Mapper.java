package org.example.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;

/**
 * Convert Model to ViewModel and otherwise.
 *
 * @param <T> Model
 * @param <V> ViewModel for the Frontend
 */
public interface Mapper<M, V> {
    /**
     * Convert a Model to T.
     *
     * @param model - the Model
     * @return the TO
     */
    M toModel(V model);

    /**
     * Convert a list of model to a list of TO.
     *
     * @param models - the list of Models
     * @return the list of TOs
     */
    List<M> toModelList(List<V> models);

    /**
     * Convert a array of Model to an array of TO.
     *
     * @param models - the array of models
     * @return the array of TOs
     */
    M[] toModelArray(V[] models);

    /**
     * Convert to a to-model.
     *
     * @param to - the to
     * @return the converted model
     */
    V toViewModel(M to);

    /**
     * Convert a list of models to a list of TOs.
     *
     * @param tos - a list of TO's
     * @return a list of models
     */
    List<V> toViewModelList(List<M> tos);

    /**
     * Convert an array of models to an array of TOs.
     *
     * @param tos - a list of TO's
     * @return a list of models
     */
    V[] toViewModelArray(M[] tos);

    /**
     * Convert a Object to String
     *
     * @param obj - the object that should be converted to a json string.
     * @return a json string of an object
     */
    String toJson(Object obj) throws JsonProcessingException;

    /**
     * Convert a json string to a specific Object.
     *
     * @param json - the json string
     * @param clazz - the class to which the json should converted
     * @param <S> - the type of the class to which should converted
     * @return an object of the json string
     */
    <S> S fromJson(String json, Class<S> clazz);
}
