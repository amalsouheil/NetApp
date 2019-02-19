package amal.souheil.netapp.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import amal.souheil.netapp.Models.GithubUser;
import amal.souheil.netapp.Models.GithubUserinfo;
import amal.souheil.netapp.R;
import amal.souheil.netapp.Utils.GithubStreams;
import amal.souheil.netapp.Utils.NetworkAsyncTask;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */


    public class MainFragment extends Fragment implements NetworkAsyncTask.Listeners  {

        // FOR DESIGN
        @BindView(R.id.fragment_main_textview)
        TextView textView;
    // 4 - Declare Subscription
    private Disposable disposable;

        public MainFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_main, container, false);
            ButterKnife.bind(this, view);
            return view;
        }
        @Override
        public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    // -----------------
        // ACTIONS
        // -----------------

        @OnClick(R.id.fragment_main_button)
        public void submit(View view) {
            //this.executeHttpRequest();
            this.executeSecondHttpRequestWithRetrofit();
           // this.streamShowString();
        }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    private void executeSecondHttpRequestWithRetrofit(){
        this.updateUIWhenStartingHTTPRequest();
        this.disposable = GithubStreams.streamFetchUserFollowingAndFetchFirstUserInfos("JakeWharton").subscribeWith(new DisposableObserver<GithubUserinfo>() {
            @Override
            public void onNext(GithubUserinfo users) {
                Log.e("TAG","On Next");
                updateUIWithUserInfo(users);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG","On Error"+Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e("TAG","On Complete !!");
            }
        });
    }


    // 1 - Execute our Stream
    private void executeHttpRequestWithRetrofit(){
        // 1.1 - Update UI
        this.updateUIWhenStartingHTTPRequest();
        // 1.2 - Execute the stream subscribing to Observable defined inside GithubStream
        this.disposable = GithubStreams.streamFetchUserFollowing("JakeWharton").subscribeWith(new DisposableObserver<List<GithubUser>>() {
            @Override
            public void onNext(List<GithubUser> users) {
                Log.e("TAG","On Next");
                // 1.3 - Update UI with list of users
                updateUIWithListOfUsers(users);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG","On Error"+Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e("TAG","On Complete !!");
            }
        });
    }

    // ------------------------------
    //  Reactive X
    // ------------------------------

    private void streamShowString(){
        this.disposable = this.getObservable()
                .map(getFunctionUpperCase())
                .flatMap(getSecondObservable()) // 2 - Adding Observable
                .subscribeWith(getSubscriber());
    }



    // 1 - Create a function that will calling a new observable
    private Function<String,String> getFunctionUpperCase(){
        return new Function<String,String>() {
            @Override
            public String apply(String s) throws Exception {
                return s.toUpperCase();
            }
        };
    }

    // 1 - Create a function that will calling a new observable
    private Function<String, Observable<String>> getSecondObservable(){
        return new Function<String, Observable<String>>() {
            @Override
            public Observable<String> apply(String previousString) throws Exception {
                return Observable.just(previousString+" I love Openclassrooms !");
            }
        };
    }


    // 1 - Create Observable
    private Observable<String> getObservable(){
        return Observable.just("Cool !");
    }
    // 2 - Create Subscriber
    private DisposableObserver<String> getSubscriber(){
        return new DisposableObserver<String>() {
            @Override
            public void onNext(String item) {
                textView.setText("Observable emits : "+item);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG","On Error"+Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e("TAG","On Complete !!");
            }
        };
    }



    // 5 - Dispose subscription
    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }



    // ----------------------------
    //  HTTP REQUEST (Retrofit Way)
    // ----------------------------

    private void executeHttpRequest(){
        new NetworkAsyncTask(this).execute("https://api.github.com/users/JakeWharton/following");
    }

    @Override
    public void onPreExecute() {
        this.updateUIWhenStartingHTTPRequest();
    }

    @Override
    public void doInBackground() { }

    @Override
    public void onPostExecute(String json) {
        this.updateUIWhenStopingHTTPRequest(json);
    }




    // ------------------
    //  UPDATE UI
    // ------------------

    private void updateUIWhenStartingHTTPRequest(){
        this.textView.setText("Downloading...");
    }

    private void updateUIWhenStopingHTTPRequest(String response){
        this.textView.setText(response);
    }
    // 3 - Update UI showing only name of users
    private void updateUIWithListOfUsers(List<GithubUser> users){
        StringBuilder stringBuilder = new StringBuilder();
        for (GithubUser user : users){
            stringBuilder.append("-"+user.getLogin()+"\n");
        }
        updateUIWhenStopingHTTPRequest(stringBuilder.toString());
    }


    private void updateUIWithUserInfo(GithubUserinfo userInfo){
        updateUIWhenStopingHTTPRequest("The first Following of Jake Wharthon is "+userInfo.getName()+" with "+userInfo.getFollowers()+" followers.");
    }

}

