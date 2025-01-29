import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";

export default function BookDetails() {
    const { id } = useParams();
    const [book, setBook] = useState(null);

    useEffect(() => {
        loadBookDetails();
    }, []);

    const loadBookDetails = async () => {
        try {
            const result = await axios.get(`http://localhost:8081/books/${id}`);
            setBook(result.data);
        } catch (error) {
            console.error("Error loading book details:", error);
        }
    };

    if (!book) return <p>Loading book details...</p>;

    return (
        <div>
            <TableContainer component={Paper}>
                <Table sx={{ minWidth: 650 }} aria-label="book details table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Attribute</TableCell>
                            <TableCell align="center">Value</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        <TableRow>
                            <TableCell>Title</TableCell>
                            <TableCell align="center">{book.title}</TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell>Author</TableCell>
                            <TableCell align="center">{book.author}</TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell>ISBN</TableCell>
                            <TableCell align="center">{book.isbn}</TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell>Quantity</TableCell>
                            <TableCell align="center">{book.quantity}</TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell>Added At</TableCell>
                            <TableCell align="center">{new Date(book.addedAt).toLocaleString()}</TableCell>
                        </TableRow>
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    );
}
