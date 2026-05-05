package com.example.todolist.ui.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todolist.ui.home.components.AddTodoFab
import com.example.todolist.ui.home.components.AppMenuItem
import com.example.todolist.ui.home.components.AppTopBar
import com.example.todolist.ui.home.event.HomeEvent
import com.example.todolist.ui.pagerTab.paper.PagerTabLayout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(vm : HomeViewModel = hiltViewModel()){
    val listTabGroup by vm.listTabGroup.collectAsStateWithLifecycle(emptyList())
    val taskDelegate = remember { vm }
    var isShowAddNoteBottomSheet by remember { mutableStateOf(false) }
    var isShowAddTaskCollectionBottomSheet by remember { mutableStateOf(false) }
    var menuListBottomSheet by remember { mutableStateOf<List<AppMenuItem>?>(null) }


    LaunchedEffect(Unit) {
        vm.eventFlow.collect {
            when (it) {
                HomeEvent.RequestAddNewCollection -> {
                    isShowAddTaskCollectionBottomSheet = true
                }
                is HomeEvent.RequestShowBottomSheetOptions -> {
                    Log.d("HomeLayout", "$it")
                    menuListBottomSheet = it.list
                }
            }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {AddTodoFab(onClick = ::oncClick)}) {
        innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {

            AppTopBar()
            if (listTabGroup.isNotEmpty()) {
                PagerTabLayout(listTabGroup, taskDelegate)
            } else {
                Text("NO TASKS AVAILABLE!!!")
            }

            if (isShowAddNoteBottomSheet) {
                var inputTaskContent by remember { mutableStateOf("") }
                ModalBottomSheet({
                    isShowAddNoteBottomSheet = false
                }) {
                    Text("Input Task Content", modifier = Modifier.fillMaxWidth())
                    TextField(
                        value = inputTaskContent,
                        onValueChange = { inputTaskContent = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button({
                        if (inputTaskContent.isNotEmpty()) {
                            taskDelegate.addNewTodoToCurrentCollection(inputTaskContent)
                            inputTaskContent = ""
                        }
                        isShowAddNoteBottomSheet = false
                    }, modifier = Modifier.fillMaxWidth()) {
                        Text("Add Task")
                    }
                }
            }

            if (isShowAddTaskCollectionBottomSheet) {
                var inputTaskCollection by remember { mutableStateOf("") }
                ModalBottomSheet({
                    isShowAddTaskCollectionBottomSheet = false
                }) {
                    Text("Input Task Collection", modifier = Modifier.fillMaxWidth())
                    TextField(
                        value = inputTaskCollection,
                        onValueChange = { inputTaskCollection = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button({
                        if (inputTaskCollection.isNotEmpty()) {
                            taskDelegate.addNewCollection(inputTaskCollection)
                            inputTaskCollection = ""
                        }
                        isShowAddTaskCollectionBottomSheet = false
                    }, modifier = Modifier.fillMaxWidth()) {
                        Text("Add Task Collection")
                    }
                }
            }

            if(!menuListBottomSheet.isNullOrEmpty()) {
                ModalBottomSheet({
                    menuListBottomSheet = null
                }) {
                    menuListBottomSheet?.forEach {
                        Text(it.title, modifier = Modifier.fillMaxWidth().clickable {
                            it.action.invoke()
                            menuListBottomSheet = null
                        }.padding(12.dp))
                    }
                }
            }
        }
        }




        }




fun oncClick() {
 Log.d("onClick", "onClick")

}