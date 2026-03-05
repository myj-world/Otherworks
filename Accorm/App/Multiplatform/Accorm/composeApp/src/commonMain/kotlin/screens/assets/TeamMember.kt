package screens.assets

import androidx.compose.ui.graphics.vector.ImageVector

data class TeamMember(
    val name: String,
    val imageUrl: String,
    val tag: ImageVector?,
    val roles: List<Role>,
    val department: String,
    val description: String
)
