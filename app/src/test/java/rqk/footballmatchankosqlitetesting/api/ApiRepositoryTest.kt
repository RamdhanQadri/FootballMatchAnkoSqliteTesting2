package rqk.footballmatchankosqlitetesting.api

import org.junit.Test

import org.mockito.Mockito

class ApiRepositoryTest {

    @Test
    fun doRequest() {
        val apiRepository = Mockito.mock(ApiRepository::class.java)
        val urlPast = "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4332"
        apiRepository.doRequest(urlPast)
        Mockito.verify(apiRepository).doRequest(urlPast)

        val urlNext = "https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4332"
        apiRepository.doRequest(urlNext)
        Mockito.verify(apiRepository).doRequest(urlNext)
    }
}