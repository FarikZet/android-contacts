package ru.yandex.practicum.contacts.presentation.filter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.function.Consumer;

import ru.yandex.practicum.contacts.databinding.ItemFilterBinding;
import ru.yandex.practicum.contacts.model.ContactType;
import ru.yandex.practicum.contacts.presentation.base.BaseListDiffCallback;
import ru.yandex.practicum.contacts.presentation.filter.model.FilterContactType;
import ru.yandex.practicum.contacts.presentation.filter.model.FilterContactTypeUI;
import ru.yandex.practicum.contacts.presentation.filter.model.FilterContactTypeUI;
import ru.yandex.practicum.contacts.utils.model.ContactTypeUtils;
import ru.yandex.practicum.contacts.utils.model.FilterContactTypeUtils;

public class FilterContactTypeAdapter extends RecyclerView.Adapter<FilterContactTypeAdapter.ViewHolder> {

    private final AsyncListDiffer<FilterContactTypeUI> differ = new AsyncListDiffer<>(
            new AdapterListUpdateCallback(this),
            new AsyncDifferConfig.Builder<>(new BaseListDiffCallback<FilterContactTypeUI>()).build()
    );

    private final Consumer<FilterContactTypeUI> clickListener;

    public FilterContactTypeAdapter(Consumer<FilterContactTypeUI> clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ItemFilterBinding binding = ItemFilterBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(differ.getCurrentList().get(position));
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public void setItems(List<FilterContactTypeUI> items) {
        differ.submitList(items);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemFilterBinding binding;

        private FilterContactTypeUI data;

        public ViewHolder(@NonNull ItemFilterBinding binding, Consumer<FilterContactTypeUI> clickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(v -> clickListener.accept(data));
            this.binding.selected.setOnClickListener(v -> clickListener.accept(data));
        }

        public void bind(FilterContactTypeUI data) {
            this.data = data;
            final int sortResId = FilterContactTypeUtils.getStringRes(data.getContactType());
            binding.text.setText(sortResId);
            binding.selected.setChecked(data.isSelected());
            if (data.getContactType() == FilterContactType.ALL){
                binding.logo.setVisibility(View.GONE);
            } else {
                final ContactType contactType = FilterContactTypeUtils.toContactType(data.getContactType());
                final int iconRes = ContactTypeUtils.getIconRes(contactType);
                binding.logo.setVisibility(View.VISIBLE);
                binding.logo.setImageResource(iconRes);
            }
        }
    }

    static class ListDiffCallback extends DiffUtil.ItemCallback<FilterContactTypeUI> {

        @Override
        public boolean areItemsTheSame(@NonNull FilterContactTypeUI oldItem, @NonNull FilterContactTypeUI newItem) {
            return oldItem.getContactType() == newItem.getContactType();
        }

        @Override
        public boolean areContentsTheSame(@NonNull FilterContactTypeUI oldItem, @NonNull FilterContactTypeUI newItem) {
            return oldItem.equals(newItem);
        }

        @Nullable
        @Override
        public Object getChangePayload(@NonNull FilterContactTypeUI oldItem, @NonNull FilterContactTypeUI newItem) {
            return newItem;
        }
    }
}
