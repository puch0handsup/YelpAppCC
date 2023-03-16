package com.example.yelpappcc.data.remote

import com.example.yelpappcc.data.remote.model.business_search.BusinessItem
import com.example.yelpappcc.data.remote.model.reviews.ReviewItem
import com.example.yelpappcc.domain.repository.RemoteRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import org.junit.Assert.*
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RemoteRepositoryImplTest {

    private lateinit var testObject : RemoteRepository
    private val mockServiceApi = mockk<YelpServiceApi>(relaxed = true)

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        testObject = RemoteRepositoryImpl(mockServiceApi)
    }

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `send latitude and longitude and retrieve a success with a list of businesses`() {
        // ASSIGNMENT
        coEvery {
            mockServiceApi.getNearbyRestaurants(
                latitude = "37.4226711",
                longitude="-122.0849872"
            )
        } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns mockk {
                every { businesses } returns listOf(
                    mockk {
                          every { name } returns "McDonald's"
                    }, mockk(), mockk(), mockk()
                )
            }
        }

        val businesses = mutableListOf<BusinessItem?>()

        val job = testScope.launch {
            val response = testObject.getNearbyRestaurants(latitude = "37.4226711", longitude="-122.0849872")
            if (response.isSuccessful){
                businesses.addAll(response.body()?.businesses as Collection<BusinessItem?>)
            }
        }

        assertEquals(4, businesses.size)
        assertEquals("McDonald's", businesses[0]?.name)

        job.cancel()
    }

    @Test
    fun `retrieve the reviews of a given restaurant ID should return three reviews`() {
        coEvery { mockServiceApi.getRestaurantReviewsById("nF-dizNkhc12m_8OK8RAhw") } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns mockk {
                every { reviews } returns listOf(
                    mockk(), mockk {
                       every { text } returns "Hakuna Matata"
                    }, mockk()
                )
            }
        }
        
        val reviews = mutableListOf<ReviewItem?>()
        
        val job = testScope.launch {
            val response = testObject.getRestaurantReviewsById("nF-dizNkhc12m_8OK8RAhw")
            if (response.isSuccessful) {
                reviews.addAll(response.body()?.reviews as Collection<ReviewItem?>)
            }
        }

        assertEquals(3, reviews.size)
        assertEquals("Hakuna Matata", reviews[1]?.text)

        job.cancel()
    }
}
