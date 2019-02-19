package amal.souheil.netapp.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import amal.souheil.netapp.Models.GithubUser;
import amal.souheil.netapp.R;

/**
 * Created by Souheil Amal on 2019-02-19
 */
public class GithubUserAdapter extends RecyclerView.Adapter<GithubUserViewHolder> {

    // FOR DATA
    private List<GithubUser> githubUsers;

    // CONSTRUCTOR
    public GithubUserAdapter(List<GithubUser> githubUsers) {
        this.githubUsers = githubUsers;
    }

    @Override
    public GithubUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_main_item, parent, false);

        return new GithubUserViewHolder(view);
    }

    // UPDATE VIEW HOLDER WITH A GITHUBUSER
    @Override
    public void onBindViewHolder(GithubUserViewHolder viewHolder, int position) {
        viewHolder.updateWithGithubUser(this.githubUsers.get(position));
    }

    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST
    @Override
    public int getItemCount() {
        return this.githubUsers.size();
    }
}
