package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static sample.Category.*;

public class MediaBrowser {

    @FXML
    private TableView<PlayList> playListTableView;
    @FXML
    private TableView<MediaFile> mediaFileTableView;
    @FXML
    private Label playListTitle;
    @FXML
    private Button addPlayListButton;
    @FXML
    private TextField searchTextField;

    final MenuItem removePlaylist = new MenuItem("Remove");
    final ContextMenu playListContextMenu = new ContextMenu(removePlaylist);

    private ObservableList<MediaFile> observableMediaFileList = FXCollections.observableArrayList();
    private ObservableList<PlayList> observableListPlayList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        removePlaylist.setOnAction(event -> removePlayList());
        addPlayListButton.setOnAction(event -> addNewPlayList());
        initialisePlaylists();
        initialiseSearchField();
        initialiseMediaFileTableView();
    }

    /**
     * Remove selected play list
     */
    private void removePlayList() {
       final PlayList selectedPlayList = playListTableView.getSelectionModel().getSelectedItem();
       if (selectedPlayList != null) {
           observableListPlayList.removeAll(selectedPlayList);
       }
    }

    /**
     * Add a new playlist to the table
     */
    private void addNewPlayList() {
        final PlayList newPlayList = new PlayList();
        newPlayList.setName("New Playlist");
        observableListPlayList.add(newPlayList);
        playListTableView.getSelectionModel().select(newPlayList);
    }

    /**
     * Sets the list of play lists available to the combo box
     */
    private void initialisePlaylists() {
        final PlayList playList1 = new PlayList();
        playList1.setName("First Playlist");
        playList1.setMediaFileList(getMediaFileList());
        observableListPlayList.add(playList1);
        playListTableView.setItems(observableListPlayList);
        playListTableView.setContextMenu(playListContextMenu);

        playListTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            final PlayList selectedPlaylist = playListTableView.getSelectionModel().getSelectedItem();
            if (selectedPlaylist != null) {
                playListTitle.setText(selectedPlaylist.getName());

                // Set media file list from playlist
                observableMediaFileList.setAll(selectedPlaylist.getMediaFileList() != null ? selectedPlaylist.getMediaFileList() : FXCollections.emptyObservableList());
                mediaFileTableView.refresh();
            }
        });
    }

    /**
     * Sets listeners to the search field to filter results.
     */
    private void initialiseSearchField() {
        // TODO add filtering on search
    }

    /**
     * Gets a list of all category descriptions to display in the combo box
     *
     * @return List of string
     */
    private ObservableList<String> getCategoryDescriptionList() {
        final List<Category> categories = Arrays.asList(Category.values());
        final List<String> stringList = categories.stream().map(Category::getDescription).collect(Collectors.toList());
        return FXCollections.observableArrayList(stringList);
    }

    /**
     * Creates list of media files to display in the table view
     *
     * TODO TEMPORARY
     *
     */
    private void initialiseMediaFileTableView() {
        observableMediaFileList.addAll(getMediaFileList());
        mediaFileTableView.setItems(observableMediaFileList);
    }

    /**
     *  Gets a list of media files.
     */
    private List<MediaFile> getMediaFileList() {
        final List<MediaFile> mediaFileList = new ArrayList<>();

        final MediaFile mediaFile1 = new MediaFile();
        mediaFile1.setName("Three Little Birds");
        mediaFile1.setCategory(Collections.singletonList(REGGAE));
        mediaFile1.setComment("Bob Marley");
        mediaFile1.setFilePath("threelittlebirds.mp3");
        mediaFile1.setMediaFileType(MediaFileType.MP3);
        mediaFileList.add(mediaFile1);

        final MediaFile mediaFile2 = new MediaFile();
        mediaFile2.setName("Back In Black");
        mediaFile2.setCategory(Collections.singletonList(ROCK));
        mediaFile2.setComment("AC/DC");
        mediaFile2.setFilePath("backinblack.mp3");
        mediaFile2.setMediaFileType(MediaFileType.MP3);
        mediaFileList.add(mediaFile2);

        final MediaFile mediaFile3 = new MediaFile();
        mediaFile3.setName("Jazz Song");
        mediaFile3.setCategory(Collections.singletonList(JAZZ));
        mediaFile3.setComment("No comment");
        mediaFile3.setFilePath("jazzsong.mp3");
        mediaFile3.setMediaFileType(MediaFileType.MP3);
        mediaFileList.add(mediaFile3);

        final MediaFile mediaFile4 = new MediaFile();
        mediaFile4.setName("Classical Song");
        mediaFile4.setCategory(Collections.singletonList(CLASSICAL));
        mediaFile4.setComment("classical");
        mediaFile4.setFilePath("classicalsong.mp3");
        mediaFile4.setMediaFileType(MediaFileType.MP3);
        mediaFileList.add(mediaFile4);

        return mediaFileList;
    }
}