package amal.souheil.netapp.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import amal.souheil.netapp.Models.GithubUser;
import amal.souheil.netapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import icepick.Injector;

/**
 * Created by Souheil Amal on 2019-02-19
 */
public class GithubUserViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_main_item_title)
    TextView textView;

    public GithubUserViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithGithubUser(GithubUser githubUser){
        this.textView.setText(githubUser.getLogin());
    }
}
