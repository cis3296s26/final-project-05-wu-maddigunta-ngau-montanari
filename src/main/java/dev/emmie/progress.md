# todo
- [ ] Move questmap into its own class
    - it will store the quests (questlines?) in it
    - it will handle the connecting of quests with lines
    - it will hold the tooltip too


# thoughts
- maybe the quests themselves dont need to both add prev and next quests, 
because this allows for a certain bug where you forget to link one direction.
I feel like it should happen automatically.
- Should the questmapView store questlines? or should they store individual quests?
- Should questline be able to return a list of edges? that would be useful..
- maybe questlineView needs to be a thing? idk we can ask claude later.
