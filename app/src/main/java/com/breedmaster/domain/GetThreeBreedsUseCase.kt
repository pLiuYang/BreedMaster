package com.breedmaster.domain

import com.breedmaster.model.Breed

/**
 * UseCase class to get three breeds from a list of breeds, where one of them is fixed and the
 * other two are random.
 */
class GetThreeBreedsUseCase {

    /**
     * three breeds = f(all breeds, target breed)
     */
    operator fun invoke(breedList: List<Breed>, pivotBreed: Breed): List<String> {
        return buildList {
            addAll(breedList.filter { it != pivotBreed }
                .shuffled()
                .take(OPTION_COUNT - 1))
            add(pivotBreed)
        }.shuffled().map { it.getDisplayName() }
    }
}

private const val OPTION_COUNT = 3