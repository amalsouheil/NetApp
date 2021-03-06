package amal.souheil.netapp;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import amal.souheil.netapp.Models.GithubUser;
import amal.souheil.netapp.Models.GithubUserinfo;
import amal.souheil.netapp.Utils.GithubStreams;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Souheil Amal on 2019-02-19
 */
@RunWith(AndroidJUnit4.class)
public class MainFragmentTest {

    @Test
    public void fetchUserFollowingTest() throws Exception {
        //1 - Get the stream
        Observable<List<GithubUser>> observableUsers = GithubStreams.streamFetchUserFollowing("JakeWharton");
        //2 - Create a new TestObserver
        TestObserver<List<GithubUser>> testObserver = new TestObserver<>();
        //3 - Launch observable
        observableUsers.subscribeWith(testObserver)
                .assertNoErrors() // 3.1 - Check if no errors
                .assertNoTimeout() // 3.2 - Check if no Timeout
                .awaitTerminalEvent(); // 3.3 - Await the stream terminated before continue

        // 4 - Get list of user fetched
        List<GithubUser> usersFetched = testObserver.values().get(0);

        // 5 - Verify if Jake Wharton follows only 12 users...
        assertThat("Jake Wharton follows only 12 users.",usersFetched.size() == 12);
    }
    @Test
    public void fetchUserInfosTest() throws Exception {
        Observable<GithubUserinfo> observableUser = GithubStreams.streamFetchUserInfos("JakeWharton");
        TestObserver<GithubUserinfo> testObserver = new TestObserver<>();

        observableUser.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        GithubUserinfo userInfo = testObserver.values().get(0);

        assertThat("Jake Wharton Github's ID is 66577.",userInfo.getId() == 66577);
    }

}