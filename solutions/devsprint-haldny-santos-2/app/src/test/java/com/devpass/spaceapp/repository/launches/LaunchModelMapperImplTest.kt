package com.devpass.spaceapp.repository.launches

import com.devpass.spaceapp.data.api.response.LaunchesResponse
import com.devpass.spaceapp.data.api.response.Links
import com.devpass.spaceapp.data.api.response.Patch
import com.devpass.spaceapp.model.Launch
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class LaunchModelMapperImplTest(val expected: Launch, val response: LaunchesResponse) {

    private lateinit var launchModelMapper: LaunchModelMapperImpl

    companion object {
        private val patch = Patch(small = "https://images2.imgbox.com/6c/cb/na1tzhHs_o.png")
        private val pathLinks = Links(patch = patch)

        @JvmStatic
        @Parameterized.Parameters(name = "{index}: should return {0} when response is {1}")
        fun data(): Collection<Array<Any>> {
            return listOf(
                arrayOf(createLaunch(status = "Fail"), createLaunchesResponse(status = false)),
                arrayOf(createLaunch(), createLaunchesResponse(status = true)),
                arrayOf(createLaunch(details = "Empty"), createLaunchesResponse(details = null)),
                arrayOf(createLaunch(date = "Bad formatted date"), createLaunchesResponse(date = "0000")),
                arrayOf(createLaunch(), createLaunchesResponse())
            )
        }

        private fun createLaunchesResponse(
            links: Links = pathLinks,
            nameRocket: String = "FalconSat",
            date: String = "1995-02-25T22:30:00.Z",
            status: Boolean = true,
            flightNumber: Int = 1,
            rocketId: String = "5e9d0d95eda69955f709d1eb",
            details: String? = "Engine failure at 33 seconds and loss of vehicle",
            launchpadId: String = "5e9e4502f5090995de566f86"
        ): LaunchesResponse {
            return LaunchesResponse(
                links = links,
                nameRocket = nameRocket,
                date = date,
                status = status,
                flightNumber = flightNumber,
                rocketId = rocketId,
                details = details,
                launchpadId = launchpadId,
            )
        }

        private fun createLaunch(
            name: String = "FalconSat",
            number: String = "1",
            date: String = "February 25, 1995",
            status: String = "Success",
            image: String = "https://images2.imgbox.com/6c/cb/na1tzhHs_o.png",
            rocketId: String = "5e9d0d95eda69955f709d1eb",
            details: String = "Engine failure at 33 seconds and loss of vehicle",
            launchpadId: String = "5e9e4502f5090995de566f86",
        ): Launch {
            return Launch(name, number, date, status, image, rocketId, details, launchpadId)
        }
    }

    @Before
    fun init() {
        launchModelMapper = LaunchModelMapperImpl()
    }

    @Test
    fun test() {
        assertEquals(expected, launchModelMapper.transformToLaunchModel(response))
    }
}
