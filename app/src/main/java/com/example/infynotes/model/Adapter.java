package com.example.infynotes.model;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infynotes.NoteDetails;
import com.example.infynotes.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements Filterable  {
    List<String> titles;
    List<String> content;
    List<String>AllTitles;

    public Adapter(List<String> title,List<String> content){
        this.titles=title;
        this.content=content;
        this.AllTitles=new ArrayList<>(AllTitles);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.note_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.noteTitle.setText(titles.get(position));
        holder.noteContent.setText(content.get(position));
        final Integer code=getRandomColor();
        holder.mCardView.setCardBackgroundColor(holder.view.getResources().getColor(code,null));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(), NoteDetails.class);
                i.putExtra("title",titles.get(position));
                i.putExtra("content",content.get(position));
                i.putExtra("code",code);
                v.getContext().startActivity(i);
            }
        });

    }

    private int getRandomColor() {

        List<Integer>colorCode=new ArrayList<>();
        colorCode.add(R.color.blue);
        colorCode.add(R.color.lightGreen);
        colorCode.add(R.color.lightPurple);
        colorCode.add(R.color.pink);
        colorCode.add(R.color.yellow);
        colorCode.add(R.color.red);
        colorCode.add(R.color.light_gray);
        colorCode.add(R.color.green);

        Random randomColor=new Random();
        int number=randomColor.nextInt(colorCode.size());
        return colorCode.get(number);


    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
     Filter exampleFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String>filteredList=new ArrayList<>();
            if (constraint==null||constraint.length()==0){
                filteredList.addAll(titles);
            }
            else{
                String filterPattern=constraint.toString().toLowerCase().trim();

                for (String item:titles){
                    if (item.toLowerCase().contains(filterPattern)){
                        filteredList.add(item);

                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            titles.clear();
            titles.addAll((Collection<? extends String>) results.values);
            notifyDataSetChanged();

        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView noteTitle,noteContent;
        View view;
        CardView mCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle=itemView.findViewById(R.id.titles);
            noteContent=itemView.findViewById(R.id.content);
            mCardView=itemView.findViewById(R.id.noteCard);
            view=itemView;
        }
    }
}
