package amal.souheil.netapp.Utils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import amal.souheil.netapp.Models.GithubUser;
import amal.souheil.netapp.Models.GithubUserinfo;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Souheil Amal on 2019-02-19
 */
public class GithubStreams {

    public static Observable<List<GithubUser>> streamFetchUserFollowing(String username){
        GithubService gitHubService = GithubService.retrofit.create(GithubService.class);
        return gitHubService.getFollowing(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);

    }

    // 1 - Create a stream that will get user infos on Github API
    public static Observable<GithubUserinfo> streamFetchUserInfos(String username) {
        GithubService gitHubService = GithubService.retrofit.create(GithubService.class);
        return gitHubService.getUserInfos(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

        // 2 - Create a stream that will :
        //     A. Fetch all users followed by "username"
        //     B. Return the first user of the list
        //     C. Fetch details of the first user
        public static Observable<GithubUserinfo> streamFetchUserFollowingAndFetchFirstUserInfos
        (String username){
            return streamFetchUserFollowing(username) // A.
                    .map(new Function<List<GithubUser>, GithubUser>() {
                        @Override
                        public GithubUser apply(List<GithubUser> users) throws Exception {
                            return users.get(0); // B.
                        }
                    })
                    .flatMap(new Function<GithubUser, Observable<GithubUserinfo>>() {
                        @Override
                        public Observable<GithubUserinfo> apply(GithubUser user) throws Exception {
                            // C.
                            return streamFetchUserInfos(user.getLogin());
                        }
                    });
        }


}