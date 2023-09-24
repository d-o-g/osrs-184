package jagex.oldscape.client.social;

import java.util.*;

public abstract class ChatterContext<T extends Chatter> {

  public final int capacity;

  public final T[] chatters;
  public final HashMap<NamePair, T> displayNameCache;
  public final HashMap<NamePair, T> previousNameCache;

  public Comparator comparator;

  public int count;

  public ChatterContext(int capacity) {
    count = 0;
    comparator = null;
    this.capacity = capacity;
    chatters = newArray(capacity);
    displayNameCache = new HashMap<>(capacity / 8);
    previousNameCache = new HashMap<>(capacity / 8);
  }

  public T getChatterByAnyName(NamePair name) {
    T chatter = getChatterByDisplayName(name);
    return chatter != null ? chatter : getChatterByPreviousName(name);
  }

  public T addAndCache(NamePair displayName, NamePair previousName) {
    if (getChatterByDisplayName(displayName) != null) {
      throw new IllegalStateException();
    }
    T chatter = newChatter();
    chatter.setName(displayName, previousName);
    add(chatter);
    cache(chatter);
    return chatter;
  }

  public final int indexOf(T chatter) {
    for (int i = 0; i < count; ++i) {
      if (chatters[i] == chatter) {
        return i;
      }
    }
    return -1;
  }

  public final void uncache(T chatter) {
    if (displayNameCache.remove(chatter.displayName) == null) {
      throw new IllegalStateException();
    }

    if (chatter.previousName != null) {
      previousNameCache.remove(chatter.previousName);
    }

  }

  public T getChatterByDisplayName(NamePair name) {
    return !name.isFormattedPresent() ? null : displayNameCache.get(name);
  }

  public int getMemberCount() {
    return count;
  }

  public boolean isFull() {
    return capacity == count;
  }

  public final void sort() {
    if (comparator == null) {
      Arrays.sort(chatters, 0, count);
    } else {
      Arrays.sort(chatters, 0, count, comparator);
    }

  }

  public boolean isCached(NamePair name) {
    if (!name.isFormattedPresent()) {
      return false;
    }
    return displayNameCache.containsKey(name) || previousNameCache.containsKey(name);
  }

  public T getChatterByPreviousName(NamePair name) {
    return !name.isFormattedPresent() ? null : previousNameCache.get(name);
  }

  abstract T newChatter();

  public void clear() {
    count = 0;
    Arrays.fill(chatters, null);
    displayNameCache.clear();
    previousNameCache.clear();
  }

  public final void remove(int index) {
    --count;
    if (index < count) {
      System.arraycopy(chatters, index + 1, chatters, index, count - index);
    }

  }

  public final void remove(T chatter) {
    int index = indexOf(chatter);
    if (index != -1) {
      remove(index);
      uncache(chatter);
    }
  }

  public final T getChatter(int index) {
    if (index >= 0 && index < count) {
      return chatters[index];
    }
    throw new ArrayIndexOutOfBoundsException(index);
  }

  public final void cache(T chatter) {
    displayNameCache.put(chatter.displayName, chatter);
    if (chatter.previousName != null) {
      T previouslyMapped = previousNameCache.put(chatter.previousName, chatter);
      if (previouslyMapped != null && previouslyMapped != chatter) {
        previouslyMapped.previousName = null;
      }
    }

  }

  abstract T[] newArray(int size);

  public final boolean remove(NamePair name) {
    T chatter = getChatterByDisplayName(name);
    if (chatter == null) {
      return false;
    }
    remove(chatter);
    return true;
  }

  public final void add(T chatter) {
    chatters[++count - 1] = chatter;
  }

  public T addAndCache(NamePair name) {
    return addAndCache(name, null);
  }

  public final void update(T chatter, NamePair displayName, NamePair previousName) {
    uncache(chatter);
    chatter.setName(displayName, previousName);
    cache(chatter);
  }

  public final void unsetComparator() {
    comparator = null;
  }

  public final void setComparator(Comparator<T> comparator) {
    if (this.comparator == null) {
      this.comparator = comparator;
    } else if (this.comparator instanceof AssociateComparator) {
      ((AssociateComparator) this.comparator).set(comparator);
    }

  }
}
